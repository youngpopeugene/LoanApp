package com.youngpopeugene.mainservice.model.api;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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