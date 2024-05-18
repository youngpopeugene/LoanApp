package com.youngpopeugene.extraservice.service;

import com.youngpopeugene.extraservice.kafka.KafkaProducer;
import com.youngpopeugene.extraservice.model.ApplicationDTO;
import com.youngpopeugene.extraservice.model.Loan;
import com.youngpopeugene.extraservice.model.LoanDTO;
import com.youngpopeugene.extraservice.model.LoanPayDTO;
import com.youngpopeugene.extraservice.repository.LoanRepository;
import com.youngpopeugene.extraservice.util.JsonObjectConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final KafkaProducer kafkaProducer;
    private final LoanRepository loanRepository;

    public void methodForTopic1(String data) {
        ApplicationDTO applicationDTO = JsonObjectConverter.fromJsonToObject(data, ApplicationDTO.class);

        double amount = applicationDTO.getAmount();
        int duration = applicationDTO.getDuration();
        double monthRate = applicationDTO.getRate() / 100 / 12;
        double income = applicationDTO.getIncome();

        double payment = monthlyPaymentCalculation(amount, duration, monthRate);
        applicationDTO.setOk((payment / income) < 0.5);

        String json = JsonObjectConverter.fromObjectToJson(applicationDTO);
        kafkaProducer.send("topic-2", json);
    }

    public void methodForTopic3(String data) {
        LoanDTO loanDTO = JsonObjectConverter.fromJsonToObject(data, LoanDTO.class);

        Loan loan = new Loan(loanDTO);
        boolean before = loanRepository.existsById(loan.getId());
        saveLoan(loan);
        boolean after = loanRepository.existsById(loan.getId());
        if (!before && after) loanDTO.setSuccess(true);

        String json = JsonObjectConverter.fromObjectToJson(loanDTO);
        kafkaProducer.send("topic-4", json);
    }

    public void methodForTopic5(String data) {
        Loan loan = loanRepository.findById(data).get();

        double amount = loan.getAmount();
        int duration = loan.getDuration();
        double monthRate = loan.getRate() / 100 / 12;
        double payment = monthlyPaymentCalculation(amount, duration, monthRate);

        double newAmount = amount - (payment - amount*monthRate); //remainingDebt
        loan.setAmount(newAmount);
        loan.setDuration(duration - 1);

        if (loan.getDuration() != 0){
            saveLoan(loan);
        } else {
            loanRepository.delete(loan);
        }

        LoanPayDTO loanPayDTO = LoanPayDTO.builder()
                .email(loan.getEmail())
                .payment(payment)
                .build();

        String json = JsonObjectConverter.fromObjectToJson(loanPayDTO);
        kafkaProducer.send("topic-6", json);
    }

    private double monthlyPaymentCalculation(double amount, int duration, double monthRate) {
        double payment =  amount * monthRate * Math.pow(1 + monthRate, duration) /
                (Math.pow(1 + monthRate, duration) - 1);
        return round(payment);
    }

    private void saveLoan(Loan loan){
        loan.setAmount(round(loan.getAmount()));
        loanRepository.save(loan);
    }

    private double round(double number){
        BigDecimal result = new BigDecimal(number);
        result = result.setScale(2, RoundingMode.HALF_EVEN);
        return result.doubleValue();
    }
}