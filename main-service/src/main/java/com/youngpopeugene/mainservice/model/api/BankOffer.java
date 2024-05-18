package com.youngpopeugene.mainservice.model.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BankOffer {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private Bank bank;

    @JsonIgnore
    @JsonProperty("min_amount")
    private int minAmount; // in rubles

    @JsonIgnore
    @JsonProperty("max_amount")
    private int maxAmount; // in rubles

    @JsonIgnore
    @JsonProperty("min_duration")
    private int minDuration; // in months

    @JsonIgnore
    @JsonProperty("min_duration")
    private int maxDuration; // in months

    @JsonProperty("loan_rate")
    private double rate;
}