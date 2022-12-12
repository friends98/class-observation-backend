package com.observationclass.repository;

import com.observationclass.entity.Account;
import com.observationclass.model.response.DropdownListResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Optional<Account> findByEmail(String email);

    Optional<Account> findByEmailAndDeleteFlag(String email, Integer deleteFlag);

    Optional<Account> findByIdAndDeleteFlag(Integer accountId, Integer deleteFlag);

    List<Account> findAllByDeleteFlag(Integer deleteFlag);

    Optional<Account> findByIdAndCampusIdAndDeleteFlag(Integer id, Integer campusId, Integer deleteFlag);

    boolean existsByEmail(String email);
    @Query(value="SELECT acc.id as value,acc.email as name\n" +
            "FROM account acc WHERE acc.campus_id=:campusId AND acc.delete_flag=:deleteFlag AND acc.email\n" +
            "like (Concat('%',:email,'%'))",nativeQuery = true)
    List<DropdownListResponse>findAllByCampusAndDelete(Integer campusId,String email,Integer deleteFlag);

    @Query(value="SELECT acc.id as value,acc.email as name\n" +
            "FROM account acc\n" +
            "INNER JOIN account_role ar ON acc.id=ar.account_id \n" +
            "WHERE acc.campus_id =:campusId AND acc.delete_flag =:deleteFlag AND ar.role_id=:role \n" +
            "AND acc.email like(Concat('%',:email,'%'))",nativeQuery =true)
    List<DropdownListResponse> findAllByCampusIdAndRole(Integer campusId, String email, Integer deleteFlag, Integer role);

}
