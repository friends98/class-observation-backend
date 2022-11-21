package com.observationclass.repository;

import com.observationclass.entity.ObservationDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObservationDetailRepository extends JpaRepository<ObservationDetail,Integer> {
    @Query(value="select * from observation_detail where observation_review_id=:observationReviewId",nativeQuery = true)
    List<ObservationDetail> findByObservationReviewId(Integer observationReviewId);
}
