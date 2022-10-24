package com.observationclass.repository;

import com.observationclass.entity.Criteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CriteriaRepository extends JpaRepository<Criteria, Integer> {

    Optional<Criteria> findCriteriaByIdAndDeleteFlag(Integer id,Integer deleteFlag);
}