package com.youngpopeugene.mainservice.service;

import com.youngpopeugene.mainservice.exception.NoMoneyException;
import com.youngpopeugene.mainservice.model.api.MoneyTransaction;
import com.youngpopeugene.mainservice.model.api.UserWallet;
import com.youngpopeugene.mainservice.repository.UserWalletRepository;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class UserWalletService {

    private final UserWalletRepository userWalletRepository;
    private final MoneyTransactionService moneyTransactionService;

    @Transactional(transactionManager = "transactionManager")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public void cashoutMoney(String userEmail, double money){
        UserWallet userWallet = getUserWalletByEmail(userEmail);
        if (userWallet != null) {
            double lessMoney = userWallet.getMoney() - money;
            if (lessMoney < 0) throw new NoMoneyException("You don't have enough money");
            userWallet.setMoney(lessMoney);
        } else throw new NoMoneyException("You're broke");
        moneyTransactionService.save(userWallet, -money);
        saveUserWallet(userWallet);
    }

    @Transactional(transactionManager = "transactionManager")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public int depositMoney(String userEmail, double money) {
        UserWallet userWallet = getUserWalletByEmail(userEmail);
        if (userWallet != null) {
            double moreMoney = userWallet.getMoney() + money;
            userWallet.setMoney(moreMoney);
        } else {
            userWallet = UserWallet
                            .builder()
                            .userEmail(userEmail)
                            .money(money)
                            .build();
        }
        MoneyTransaction moneyTransaction = moneyTransactionService.save(userWallet, money);
        saveUserWallet(userWallet);
        return moneyTransaction.getId();
    }

    @Transactional(transactionManager = "transactionManager")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public void cancelDepositMoney(int moneyTransactionId) {
        MoneyTransaction moneyTransaction =
                moneyTransactionService.getMoneyTransactionById(moneyTransactionId);
        UserWallet userWallet = moneyTransaction.getUserWallet();
        double money = moneyTransaction.getChange();
        double lessMoney = userWallet.getMoney() - money;
        userWallet.setMoney(lessMoney);
        moneyTransactionService.delete(moneyTransactionId);
        saveUserWallet(userWallet);
    }

    @Transactional(transactionManager = "transactionManager")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public void payLoan(String userEmail, double money){
        UserWallet userWallet = getUserWalletByEmail(userEmail);
        double lessMoney = userWallet.getMoney() - money;
        userWallet.setMoney(lessMoney);
        moneyTransactionService.save(userWallet, -money);
        saveUserWallet(userWallet);
    }

    public UserWallet balance(String userEmail) {
        return getUserWalletByEmail(userEmail);
    }

    public UserWallet getUserWalletByEmail(String email){
        return userWalletRepository.findByEmail(email).orElse(null);
    }

    public void saveUserWallet(UserWallet userWallet){
        userWallet.setMoney(round(userWallet.getMoney()));
        userWalletRepository.save(userWallet);
    }

    private double round(double number){
        BigDecimal result = new BigDecimal(number);
        result = result.setScale(2, RoundingMode.HALF_EVEN);
        return result.doubleValue();
    }
}