package com.youngpopeugene.mainservice.model.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Application {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @JsonIgnore
    private String email;

    @NotNull
    @Min(value = 1)
    @JsonProperty("bank_offer_id")
    private int bankOfferId;

    @NotNull
    @Min(value = 1)
    private double amount; // in rubles

    @NotNull
    @Min(value = 1)
    private int duration; // in months

    @NotNull
    @Min(value = 1)
    private int income; // in rubles

    @Enumerated(EnumType.STRING)
    private Status status;

    public Application(ApplicationDTO applicationDTO) {
        this.email = applicationDTO.getEmail();
        this.bankOfferId = applicationDTO.getBankOfferId();
        this.amount = applicationDTO.getAmount();
        this.duration = applicationDTO.getDuration();
        this.income = applicationDTO.getIncome();
    }
}