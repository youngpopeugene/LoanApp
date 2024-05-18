package com.youngpopeugene.extraservice.repository;

import com.youngpopeugene.extraservice.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan, String> {
}