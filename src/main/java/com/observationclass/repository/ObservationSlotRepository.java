package com.observationclass.repository;

import com.observationclass.entity.ObservationSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ObservationSlotRepository extends JpaRepository<ObservationSlot,Integer> {
    Optional<ObservationSlot> findByObservationPlan(Integer planId);

    Optional<ObservationSlot> findByIdAndDeleteFlag(Integer id, Integer deleteFlag);
}
