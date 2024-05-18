package com.youngpopeugene.mainservice.repository;

import com.youngpopeugene.mainservice.model.auth.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    @Query("""
      select t from Token t inner join User u
      on t.user.email = u.email
      where u.email = :email and (t.expired = false or t.revoked = false)
      """)
    List<Token> findAllValidTokenByUser(String email);

    Optional<Token> findByToken(String token);
}