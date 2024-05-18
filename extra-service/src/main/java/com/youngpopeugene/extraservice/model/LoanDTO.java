package com.youngpopeugene.extraservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.quartz.JobKey;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanDTO {
    private String id;
    private String email;
    private double amount;
    private int duration;
    private double rate;
    private String jobKeyName;
    private int moneyTransactionId;
    private int applicationId;
    private boolean success;
}