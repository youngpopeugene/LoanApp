package com.youngpopeugene.mainservice.repository;

import com.youngpopeugene.mainservice.model.api.BankOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankOfferRepository extends JpaRepository<BankOffer, Integer> {
    @Query("select bo " +
            "from BankOffer bo " +
            "where bo.maxAmount >= ?1 and bo.minAmount <= ?1 " +
            "and bo.maxDuration >= ?2 and bo.minDuration <= ?2")
    List<BankOffer> findSuitableBankOffers(double amount, int duration);
}