package com.youngpopeugene.mainservice.model.api;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class MoneyTransaction {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_wallet_id")
    private UserWallet userWallet;

    private double change;
}