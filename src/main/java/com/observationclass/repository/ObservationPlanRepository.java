package com.observationclass.repository;

import com.observationclass.entity.ObservationPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface ObservationPlanRepository extends JpaRepository<ObservationPlan,Integer> {
    Optional<ObservationPlan>  findByIdAndDeleteFlag(Integer id,Integer deleteFlag);
}
