package com.observationclass.repository;

import com.observationclass.entity.Criteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CriteriaRepository extends JpaRepository<Criteria, Integer> {

    Optional<Criteria> findByIdAndDeleteFlag(Integer id, Integer deleteFlag);

    @Query(value = "SELECT * FROM criteria WHERE delete_flag=:deleteFlag ORDER BY id ASC", nativeQuery = true)
    List<Criteria> findAllByDeleteFlag(Integer deleteFlag);
}
