package com.observationclass.repository;

import com.observationclass.entity.ObservationSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ObservationSlotRepository extends JpaRepository<ObservationSlot,Integer> {
    Optional<ObservationSlot> findByObservationPlan(Integer planId);

    Optional<ObservationSlot> findByIdAndDeleteFlag(Integer id, Integer deleteFlag);
    @Query(value = "select * from observation_slot os \n" +
            "left join observation_plan op on os.plan_id =op.id\n" +
            "where op.semester_id =:semesterId and os.head_subject =:accountId and os.delete_flag =0",nativeQuery = true)
    List<ObservationSlot> findAllByHeadSubjectAndSemester(Integer accountId, Integer semesterId);
}
