package com.observationclass.repository;

import com.observationclass.entity.ObservationDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObservationDetailRepository extends JpaRepository<ObservationDetail,Integer> {
}
