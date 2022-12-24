package com.observationclass.repository.dao;

import com.observationclass.model.response.ListObservationReviewResponse;
import com.observationclass.utils.Utils;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ObservationReviewDao {
    @Autowired
    private EntityManager entityManager;

    public List<Object> listObservationReview(Integer campusId, Integer semesterId, Integer accountId) {
        List<Object> listObservationReview =new ArrayList<>();
        Session session = entityManager.unwrap(Session.class);
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT os.id as id,account.user_name as lectureName,os.slot_time as slotTime,slot.slot_name as slotName\n" +
                ",os.class_name as className,room.room_name as roomName,department.department_name as departmentName\n" +
                ",subject.subject_name as subjectName\n" +
                "FROM observation_slot os\n" +
                "LEFT JOIN account ON os.account_id = account.id\n" +
                "LEFT JOIN slot ON os.slot_id = slot.id\n" +
                "LEFT JOIN room ON os.room_id = room.id\n" +
                "LEFT JOIN subject ON os.subject_id = subject.id\n" +
                "LEFT JOIN observation_plan ON os.plan_id = observation_plan.id\n" +
                "LEFT JOIN department ON department.id = observation_plan.department_id\n" +
                "WHERE observation_plan.delete_flag=0 AND observation_plan.plan_status=1 AND observation_plan.campus_id=:campusId AND observation_plan.semester_id=:semesterId\n" +
                "AND (os.account_id2=:accountId or os.account_id1=:accountId or os.head_training=:accountId or os.head_subject=:accountId)");
        NativeQuery<ListObservationReviewResponse> query =session.createNativeQuery(sb.toString());
        Utils.addScalr(query,ListObservationReviewResponse.class);
        query.setParameter("campusId", campusId);
        query.setParameter("semesterId",semesterId);
        query.setParameter("accountId", accountId);
        session.close();
        listObservationReview.addAll(query.getResultList());
        return listObservationReview;
    }
    public List<Object> listResultObservationReview(Integer campusId, Integer semesterId, Integer accountId) {
        List<Object> listObservationReview =new ArrayList<>();
        Session session = entityManager.unwrap(Session.class);
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT os.id as id,account.user_name as lectureName,os.slot_time as slotTime,slot.slot_name as slotName\n" +
                ",os.class_name as className,room.room_name as roomName,department.department_name as departmentName\n" +
                ",subject.subject_name as subjectName\n" +
                "FROM observation_slot os\n" +
                "LEFT JOIN account ON os.account_id = account.id\n" +
                "LEFT JOIN slot ON os.slot_id = slot.id\n" +
                "LEFT JOIN room ON os.room_id = room.id\n" +
                "LEFT JOIN subject ON os.subject_id = subject.id\n" +
                "LEFT JOIN observation_plan ON os.plan_id = observation_plan.id\n" +
                "LEFT JOIN department ON department.id = observation_plan.department_id\n" +
                "WHERE observation_plan.delete_flag=0 AND observation_plan.campus_id=:campusId AND observation_plan.semester_id=:semesterId\n" +
                "AND os.account_id=:accountId");
        NativeQuery<ListObservationReviewResponse> query =session.createNativeQuery(sb.toString());
        Utils.addScalr(query,ListObservationReviewResponse.class);
        query.setParameter("campusId", campusId);
        query.setParameter("semesterId",semesterId);
        query.setParameter("accountId", accountId);
        session.close();
        listObservationReview.addAll(query.getResultList());
        return listObservationReview;
    }

}
