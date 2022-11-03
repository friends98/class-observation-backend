package com.observationclass.repository.dao;

import com.observationclass.model.response.AccountResponse;
import com.observationclass.utils.Utils;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AccountDao {
    @Autowired
    private EntityManager entityManager;
    public List<Object> listAccountByRole(Integer roleId) {
        List<Object> listAccountResponse = new ArrayList<>();
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
        @SuppressWarnings("unchecked")
        NativeQuery<AccountResponse> query = session.createNativeQuery(sb.toString());
        Utils.addScalr(query,AccountResponse.class);
        query.setParameter("roleId",roleId);
        session.close();
        listAccountResponse.addAll(query.getResultList());

        return listAccountResponse;
    }
}
