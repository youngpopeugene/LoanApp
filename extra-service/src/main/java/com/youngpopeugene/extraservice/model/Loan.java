package com.youngpopeugene.extraservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.quartz.JobKey;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Loan {
    @Id
    private String id;
    private String email;
    private double amount;
    private int duration;
    private double rate;

    public Loan(LoanDTO loanDTO) {
        this.id = loanDTO.getId();
        this.email = loanDTO.getEmail();
        this.amount = loanDTO.getAmount();
        this.duration = loanDTO.getDuration();
        this.rate = loanDTO.getRate();
    }
}