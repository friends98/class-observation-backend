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

    @Query(value = "SELECT acc.id ,acc.user_name,acc.email,campus.campus_name\n" +
            "FROM account acc \n" +
            "LEFT JOIN account_role accr ON accr.account_id = acc.id\n" +
            "LEFT JOIN campus campus ON acc.campus_id=campus.id\n" +
            "WHERE accr.role_id=:roleId",nativeQuery = true)
    List<AccountResponse> findAccountByRole(Integer roleId);

    List<Account> findAllByDeleteFlag(Integer deleteFlag);

    boolean existsByEmail(String email);

}
