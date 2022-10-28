package com.observationclass.repository;

import com.observationclass.entity.ObservationReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObservationReviewRepository extends JpaRepository<ObservationReview,Integer> {
}
