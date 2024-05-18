package com.youngpopeugene.mainservice.repository;

import com.youngpopeugene.mainservice.model.api.Application;
import com.youngpopeugene.mainservice.model.api.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {
    @Query("select a " +
            "from Application a " +
            "where a.email = ?1")
    List<Application> findApplicationsByEmail(String email);

    @Query("select a " +
            "from Application a " +
            "join BankOffer bo on a.bankOfferId = bo.id " +
            "where a.status = 'PENDING' and bo.bank = ?1")
    List<Application> findApplicationsWithStatusPending(Bank bank);
}