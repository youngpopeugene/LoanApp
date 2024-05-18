package com.youngpopeugene.mainservice.kafka;

import com.youngpopeugene.mainservice.util.JsonObjectConverter;
import com.youngpopeugene.mainservice.model.api.*;
import com.youngpopeugene.mainservice.service.ApplicationService;
import com.youngpopeugene.mainservice.service.SchedulerService;
import com.youngpopeugene.mainservice.service.UserWalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    @Autowired
    private SchedulerService schedulerService;
    private final UserWalletService userWalletService;
    private final ApplicationService applicationService;

    @KafkaListener(topics = "topic-2", groupId = "group")
    public void listener2(String data) {
        ApplicationDTO applicationDTO = JsonObjectConverter.fromJsonToObject(data, ApplicationDTO.class);
        Application application = new Application(applicationDTO);
        if (applicationDTO.isOk()) application.setStatus(Status.PENDING);
        else application.setStatus(Status.DECLINED);
        applicationService.saveApplication(application);
    }

    @KafkaListener(topics = "topic-4", groupId = "group")
    public void listener4(String data) {
        LoanDTO loanDTO = JsonObjectConverter.fromJsonToObject(data, LoanDTO.class);
        if (!loanDTO.isSuccess()){
            int applicationId = loanDTO.getApplicationId();
            Application application = applicationService.getApplicationById(applicationId);
            application.setStatus(Status.PENDING);
            applicationService.saveApplication(application);
            int moneyTransactionId = loanDTO.getMoneyTransactionId();
            userWalletService.cancelDepositMoney(moneyTransactionId);
            String jobKeyName = loanDTO.getJobKeyName();
            schedulerService.deleteJob(jobKeyName);
        }
    }

    @KafkaListener(topics = "topic-6", groupId = "group")
    public void listener6(String data) {
        LoanPayDTO loanPayDTO = JsonObjectConverter.fromJsonToObject(data, LoanPayDTO.class);
        userWalletService.payLoan(loanPayDTO.getEmail(), loanPayDTO.getPayment());
    }
}