package com.observationclass.repository;

import com.observationclass.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountReqository extends JpaRepository<Account, Integer> {
    Optional<Account> findByEmail(String email);

    boolean existsByEmail(String email);
}
