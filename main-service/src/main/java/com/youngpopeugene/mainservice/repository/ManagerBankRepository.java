package com.youngpopeugene.mainservice.repository;

import com.youngpopeugene.mainservice.model.api.ManagerBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerBankRepository extends JpaRepository<ManagerBank, Integer> {
    @Query("select mb " +
            "from ManagerBank mb " +
            "where mb.managerEmail = ?1 ")
    Optional<ManagerBank> findByEmail(String email);
}