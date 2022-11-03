package com.observationclass.repository;

import com.observationclass.entity.Account;
import com.observationclass.model.response.AccountResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Optional<Account> findByEmail(String email);

    Optional<Account> findByEmailAndDeleteFlag(String email,Integer deleteFlag);

    Optional<Account> findByIdAndDeleteFlag(Integer accountId, Integer deleteFlag);



    List<Account> findAllByDeleteFlag(Integer deleteFlag);


    boolean existsByEmail(String email);

}
