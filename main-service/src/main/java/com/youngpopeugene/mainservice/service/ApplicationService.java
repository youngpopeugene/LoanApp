package com.youngpopeugene.mainservice.service;

import com.youngpopeugene.mainservice.util.JsonObjectConverter;
import com.youngpopeugene.mainservice.exception.*;
import com.youngpopeugene.mainservice.kafka.KafkaProducer;
import com.youngpopeugene.mainservice.model.api.*;
import com.youngpopeugene.mainservice.model.api.ReviewDTO;
import com.youngpopeugene.mainservice.repository.ApplicationRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.quartz.JobBuilder.newJob;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    @Autowired
    private KafkaProducer kafkaProducer;

    private final SchedulerService schedulerService;
    private final BankOfferService bankOfferService;
    private final UserWalletService userWalletService;
    private final ManagerBankService managerBankService;
    private final ApplicationRepository applicationRepository;

    public List<Application> getApplications(String email){
        return getApplicationsByEmail(email);
    }

    @Transactional(transactionManager = "transactionManager")
    public void registerApplication(String email, Application application){
        if (userWalletService.balance(email) != null &&
                userWalletService.balance(email).getMoney() < 0)
            throw new NoMoneyException("You can't register an application, you're a debtor");

        if (!validateApplication(application)) throw new ValidateException();
        application.setEmail(email);

        ApplicationDTO applicationDTO = new ApplicationDTO(
                application,
                bankOfferService.getBankOfferById(application.getBankOfferId()).getRate()
        );

        String json = JsonObjectConverter.fromObjectToJson(applicationDTO);
        kafkaProducer.send("topic-1", json);
    }

    public List<Application> getApplicationsForReview(String email) {
        Bank managerBank = managerBankService.getManagerBankByEmail(email).getBank();
        return applicationRepository.findApplicationsWithStatusPending(managerBank);
    }

    @Transactional(transactionManager = "transactionManager")
    public void reviewApplication(String managerEmail, ReviewDTO reviewDTO) {
        if (!validateReview(reviewDTO)) throw new ValidateException();
        Application application = getApplicationById(reviewDTO.getApplicationId());

        Bank managerBank = managerBankService.getManagerBankByEmail(managerEmail).getBank();
        int boId = application.getBankOfferId();
        BankOffer bo = bankOfferService.getBankOfferById(boId);
        if (!bo.getBank().equals(managerBank))
            throw new ValidateException("You can review only applications from your bank");

        if (application.getStatus() != Status.PENDING)
            throw new ValidateException("You can review only applications with status 'PENDING'");

        Status newStatus = reviewDTO.getNewStatus();
        application.setStatus(newStatus);
        saveApplication(application);

        if (newStatus == Status.APPROVED){
            double money = application.getAmount();
            String userEmail = application.getEmail();
            int moneyTransactionId = userWalletService.depositMoney(userEmail, money);

            String loanId = UUID.randomUUID().toString();

            JobKey jobKey;
            try {
                jobKey = schedulerService.startSendingJob(loanId, application.getDuration());
            } catch (SchedulerException e) {
                throw new JobException("Problems with starting job");
            }

            registerLoan(loanId, application, jobKey, moneyTransactionId);
        }
    }

    @Transactional(transactionManager = "transactionManager", propagation = Propagation.MANDATORY)
    public void registerLoan(String loanId, Application application, JobKey jobKey, int moneyTransactionId) {
        LoanDTO loanDTO = LoanDTO.builder()
                .id(loanId)
                .email(application.getEmail())
                .amount(application.getAmount())
                .duration(application.getDuration())
                .rate(bankOfferService.getBankOfferById(application.getBankOfferId()).getRate())
                .jobKeyName(jobKey.getName())
                .moneyTransactionId(moneyTransactionId)
                .applicationId(application.getId())
                .success(false)
                .build();
        String json = JsonObjectConverter.fromObjectToJson(loanDTO);
        kafkaProducer.send("topic-3", json);
    }

    public boolean validateReview(ReviewDTO reviewDTO){
        Set<ConstraintViolation<ReviewDTO>> violations =
                Validation.buildDefaultValidatorFactory().getValidator().validate(reviewDTO);
        return violations.isEmpty()
                && (reviewDTO.getNewStatus() == Status.APPROVED || reviewDTO.getNewStatus() == Status.DECLINED);
    }

    public boolean validateApplication(Application application){
        Set<ConstraintViolation<Application>> violations =
                Validation.buildDefaultValidatorFactory().getValidator().validate(application);
        if (!violations.isEmpty()) return false;
        BankOffer bankOffer = bankOfferService.getBankOfferById(application.getBankOfferId());
        double amount = application.getAmount();
        int duration = application.getDuration();
        int minAmount = bankOffer.getMinAmount();
        int maxAmount = bankOffer.getMaxAmount();
        int minDuration = bankOffer.getMinDuration();
        int maxDuration = bankOffer.getMaxDuration();
        return amount <= maxAmount && amount >= minAmount &&
                duration <= maxDuration && duration >= minDuration;
    }

    public List<Application> getApplicationsByEmail(String email){
        return applicationRepository.findApplicationsByEmail(email);
    }

    public Application getApplicationById(int id){
        return applicationRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Application not found")
        );
    }

    public void saveApplication(Application application){
        applicationRepository.save(application);
    }
}