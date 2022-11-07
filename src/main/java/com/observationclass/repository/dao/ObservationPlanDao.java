package com.observationclass.repository.dao;

import com.observationclass.model.response.AccountResponse;
import com.observationclass.model.response.SearchObservationPlanResponse;
import com.observationclass.utils.Utils;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ObservationPlanDao {
    @Autowired
    private EntityManager entityManager;

    public List<Object> listSearchObservationPlan(Integer campusId, Integer semesterId) {
        List<Object> listSearchObservationPlan = new ArrayList<>();
        Session session = entityManager.unwrap(Session.class);
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT op.id as id,department.department_name as departmentName,\n" +
                "op.created_at as createdAt,op.updated_at as updatedAt,op.plan_status as planStatus\n" +
                "FROM observation_plan op\n" +
                "LEFT JOIN campus ON op.campus_id = campus.id\n" +
                "LEFT JOIN semester ON op.semester_id = semester.id\n" +
                "LEFT JOIN department ON op.department_id = department.id\n" +
                "WHERE op.delete_flag=0 AND op.campus_id=:campusId");
        if (semesterId != null) {
            sb.append(" AND op.semester_id=:semesterId");
        }
//        sb.append(" ORDER BY department.department_name ASC");
        NativeQuery<SearchObservationPlanResponse> query = session.createNativeQuery(sb.toString());
        Utils.addScalr(query, SearchObservationPlanResponse.class);
        query.setParameter("campusId", campusId);
        query.setParameter("semesterId", semesterId);
        session.close();
        listSearchObservationPlan.addAll(query.getResultList());
        return listSearchObservationPlan;
    }
}
