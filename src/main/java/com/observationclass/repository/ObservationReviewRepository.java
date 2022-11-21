package com.observationclass.repository;

import com.observationclass.entity.ObservationReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ObservationReviewRepository extends JpaRepository<ObservationReview,Integer> {
    @Query(value = "select * from observation_review where observation_slot_id =:slotId and account_id=:accountId",nativeQuery = true)
    Optional<ObservationReview> findByAccountAndObservationSlot(Integer slotId,Integer accountId);
}
