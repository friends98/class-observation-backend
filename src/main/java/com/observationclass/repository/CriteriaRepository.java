package com.observationclass.repository;

import com.observationclass.entity.Criteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CriteriaRepository extends JpaRepository<Criteria, Integer> {

    Optional<Criteria> findCriteriaByIdAndCampusIdAndDeleteFlag(Integer id, Integer campusId, Integer deleteFlag);
    List<Criteria> findAllByCampusIdAndDeleteFlag(Integer campusId,Integer deleteFlag);
}
