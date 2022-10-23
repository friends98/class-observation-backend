package com.observationclass.repository;

import com.observationclass.entity.ObservationPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ObservationPlanRepository extends JpaRepository<ObservationPlan,Integer> {

}
