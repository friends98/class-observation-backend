package com.observationclass.repository.dao;

import com.observationclass.model.response.AccountResponse;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AccountDao {
    @Autowired
    private EntityManager entityManager;
    public List<AccountResponse> listAccountByRole(Integer roleId) {
        List<AccountResponse> listAccountResponse = new ArrayList<>();
        Session session = entityManager.unwrap(Session.class);
        StringBuilder sb =new StringBuilder();
        sb.append("SELECT acc.id as id ,acc.user_name as userName,\n" +
                "acc.email as email,campus.campus_name as campusName from account acc \n" +
                "LEFT JOIN account_role accr ON accr.account_id = acc.id\n" +
                "LEFT JOIN campus campus ON acc.campus_id=campus.id\n" +
                "WHERE acc.delete_flag=0");
        if(roleId!=null){
            sb.append(" AND accr.role_id=:roleId");
        }
        NativeQuery<AccountResponse> query=session.createNativeQuery(sb.toString());
        query.setParameter("roleId",roleId);
        session.close();
        return query.getResultList();
    }
}
