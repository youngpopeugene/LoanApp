package com.youngpopeugene.mainservice.model.api;

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
public class LoanRequirementsDTO {
    @NotNull
    @Min(value = 1)
    private double amount; // in rubles
    @NotNull
    @Min(value = 1)
    private int duration; // in months
}