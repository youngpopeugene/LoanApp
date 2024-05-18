package com.youngpopeugene.extraservice.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanPayDTO {
    private String email;
    private double payment;
}