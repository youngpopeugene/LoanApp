package com.youngpopeugene.mainservice.repository;

import com.youngpopeugene.mainservice.model.api.UserWallet;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserWalletRepository extends JpaRepository<UserWallet, Integer> {
    @Query("select uw " +
            "from UserWallet uw " +
            "where uw.userEmail = ?1 ")
    Optional<UserWallet> findByEmail(String email);

    @Override
    <S extends UserWallet> S save(S entity);
}