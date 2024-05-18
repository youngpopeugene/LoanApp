package com.youngpopeugene.mainservice.repository;

import com.youngpopeugene.mainservice.model.api.MoneyTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoneyTransactionRepository extends JpaRepository<MoneyTransaction, Integer> {
}