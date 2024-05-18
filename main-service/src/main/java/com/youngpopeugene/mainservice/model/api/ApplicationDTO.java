package com.youngpopeugene.mainservice.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDTO {

    private String email;
    @JsonProperty("bank_offer_id")
    private int bankOfferId;
    private double amount; // in rubles
    private int duration; // in months
    private int income; // in rubles
    private double rate;
    private boolean ok;

    public ApplicationDTO(Application application, double rate) {
        this.email = application.getEmail();
        this.bankOfferId = application.getBankOfferId();
        this.amount = application.getAmount();
        this.duration = application.getDuration();
        this.income = application.getIncome();
        this.rate = rate;
        this.ok = false;
    }
}