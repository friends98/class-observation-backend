package com.observationclass.repository;

import com.observationclass.entity.ObservationPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface ObservationPlanRepository extends JpaRepository<ObservationPlan,Integer> {
    Optional<ObservationPlan>  findByIdAndPlanStatus(Integer id,Integer planStatus);

}
