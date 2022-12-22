package com.observationclass.repository.dao;

import com.observationclass.model.response.ResultObservationSlotResponse;
import com.observationclass.model.response.SearchObservationSlotByPlan;
import com.observationclass.model.response.SearchObservationSlotResponse;
import com.observationclass.utils.Utils;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ObservationSlotDao {
    @Autowired
    private EntityManager entityManager;
    // lấy danh sách slot theo kế hoạch
    public List<Object> listOfObservationSlotByPlanId(Integer planId) {
        List<Object> listOfObservationSlot =new ArrayList<>();
        Session session = entityManager.unwrap(Session.class);
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT os.id as id,os.plan_id as planId,acc.user_name as accountName,os.slot_time as slotTime,slot" +
                ".slot_range as slot,\n" +
                "room.room_name as roomName,subject.subject_code as subjectCode,subject.subject_name as subjectName,\n" +
                "os.class_name as className,os.reason as reason,\n" +
                "acc1.user_name as accountName1,acc2.user_name accountName2,os.result as result\n" +
                "FROM observation_slot os\n" +
                "LEFT JOIN account acc ON os.account_id = acc.id\n" +
                "LEFT JOIN account acc0 ON os.head_training = acc0.id \n" +
                "LEFT JOIN account acc1 ON os.account_id1 = acc1.id\n" +
                "LEFT JOIN account acc2 ON os.account_id2 = acc2.id\n" +
                "LEFT JOIN observation_plan op ON op.id = os.plan_id\n" +
                "LEFT JOIN slot ON slot.id = os.slot_id\n" +
                "LEFT JOIN room ON room.id = os.room_id\n" +
                "LEFT JOIN subject ON subject.id = os.subject_id\n" +

                "WHERE os.plan_id=:planId ORDER BY os.id ASC");
        NativeQuery<SearchObservationSlotByPlan> query = session.createNativeQuery(sb.toString());
        Utils.addScalr(query,SearchObservationSlotByPlan.class);
        query.setParameter("planId", planId);
        session.close();
        listOfObservationSlot.addAll(query.getResultList());
        return listOfObservationSlot;
    }
    //danh sach slot du gio theo kì và theo CNBM
    public List<Object>  listObservationSlotBySemester(Integer semesterId, Integer accountId){
        List<Object> listObservationReview =new ArrayList<>();
        Session session = entityManager.unwrap(Session.class);
        StringBuilder sb = new StringBuilder();
//        sb.append("SELECT os.id as id,os.plan_id as planId,acc.user_name as userName,os.slot_time as slotTime,slot.slot_range as slot,\n" +
//                "room.room_name as roomName,subject.subject_code as subjectCode,subject.subject_name as subjectName,\n" +
//                "os.class_name as className,os.reason as reason,\n" +
//                "acc0.user_name as headTraining,acc1.user_name as lecture1,acc2.user_name lecture2\n" +
//                "FROM observation_slot os\n" +
//                "LEFT JOIN account acc ON os.account_id = acc.id\n" +
//                "LEFT JOIN account acc0 ON os.head_training = acc0.id \n" +
//                "LEFT JOIN account acc1 ON os.account_id1 = acc1.id\n" +
//                "LEFT JOIN account acc2 ON os.account_id2 = acc2.id\n" +
//                "LEFT JOIN observation_plan op ON op.id = os.plan_id\n" +
//                "LEFT JOIN slot ON slot.id = os.slot_id\n" +
//                "LEFT JOIN room ON room.id = os.room_id\n" +
//                "LEFT JOIN subject ON subject.id = os.subject_id\n" +
//                "WHERE op.semester_id=:semesterId and os.head_subject=:accountId");
        sb.append("SELECT os.id as id,os.account_id as accountId,os.plan_id as planId,os.slot_time as slotTime,os" +
                ".slot_id as " +
                "slotId," +
                "os" +
                ".subject_id as subjectId,s.subject_name  as subjectName,\n" +
                "os.room_id as roomId,os.reason as reason,os.class_name as className,os.account_id1 as accountId1,os" +
                ".account_id2 as accountId2,os.\"result\" as result\n" +
                "FROM observation_slot os \n" +
                "LEFT JOIN observation_plan op ON os.plan_id =op.id\n" +
                "LEFT JOIN subject s ON s.id =os.subject_id \n" +
                "WHERE op.semester_id =:semesterId AND os.head_subject =:accountId AND os.delete_flag =0 ORDER BY os" +
                ".id ASC");
        NativeQuery<SearchObservationSlotResponse> query = session.createNativeQuery(sb.toString());
        Utils.addScalr(query,SearchObservationSlotResponse.class);
        query.setParameter("semesterId", semesterId);
        query.setParameter("accountId", accountId);
        session.close();
        listObservationReview.addAll(query.getResultList());
        System.out.println("size "+listObservationReview.size());
        return listObservationReview;

    }
    // hien tai not used
    public List<Object> resultObservationSlot(Integer observationSlotId){
        List<Object> resultObservationSlot =new ArrayList<>();
        Session session = entityManager.unwrap(Session.class);
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT acc.user_name AS lectureName,ore.advantage AS advantage,ore.disadvantage AS disadvantage,\n" +
                "ore.comment AS comment,ore.total_point AS totalPoint\n" +
                "FROM observation_review ore\n" +
                "LEFT JOIN observation_slot os ON os.id=ore.observation_slot_id\n" +
                "LEFT JOIN account acc ON os.account_id = acc.id\n" +
                "LEFT JOIN account acc1 ON acc1.id = ore.account_id\n" +
                "WHERE os.id=:observationSlotId");
        NativeQuery<ResultObservationSlotResponse> query = session.createNativeQuery(sb.toString());
        Utils.addScalr(query,ResultObservationSlotResponse.class);
        query.setParameter("observationSlotId", observationSlotId);
        session.close();
        resultObservationSlot.addAll(query.getResultList());
        return resultObservationSlot;
    }
    // hien tai not used
    public List<Object> resultObservationSlot1(Integer observationSlotId){
        List<Object> resultObservationSlot =new ArrayList<>();
        Session session = entityManager.unwrap(Session.class);
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT acc.user_name AS lectureName,ore.advantage AS advantage,ore.disadvantage AS disadvantage,\n" +
                "ore.comment AS comment,ore.total_point AS totalPoint\n" +
                "FROM observation_review ore\n" +
                "LEFT JOIN observation_slot os ON os.id=ore.observation_slot_id\n" +
                "LEFT JOIN account acc ON os.account_id = acc.id\n" +
                "LEFT JOIN account acc1 ON acc1.id = ore.account_id\n" +
                "WHERE os.id=:observationSlotId");
        NativeQuery<ResultObservationSlotResponse> query = session.createNativeQuery(sb.toString());
        Utils.addScalr(query,ResultObservationSlotResponse.class);
        query.setParameter("observationSlotId", observationSlotId);
        session.close();
        resultObservationSlot.addAll(query.getResultList());
        return resultObservationSlot;
    }
}
