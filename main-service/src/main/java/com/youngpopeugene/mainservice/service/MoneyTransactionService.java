package com.youngpopeugene.mainservice.service;

import com.youngpopeugene.mainservice.model.api.MoneyTransaction;
import com.youngpopeugene.mainservice.model.api.UserWallet;
import com.youngpopeugene.mainservice.repository.MoneyTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MoneyTransactionService {

    private final MoneyTransactionRepository moneyTransactionRepository;

    public MoneyTransaction getMoneyTransactionById(int moneyTransactionId){
        return moneyTransactionRepository.findById(moneyTransactionId).get();
    }

    public MoneyTransaction save(UserWallet userWallet, double money){
        var moneyTransaction = MoneyTransaction
                .builder()
                .userWallet(userWallet)
                .change(money)
                .build();
        return moneyTransactionRepository.save(moneyTransaction);
    }

    public void delete(int moneyTransactionId){
        moneyTransactionRepository.deleteById(moneyTransactionId);
    }
}