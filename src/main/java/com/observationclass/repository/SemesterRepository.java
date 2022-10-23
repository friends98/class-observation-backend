package com.observationclass.repository;

import com.observationclass.entity.Semester;
import com.observationclass.model.response.DropdownListResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SemesterRepository extends JpaRepository<Semester,Integer> {
    @Query(value="SELECT s.id as value,s.semester_name as name FROM semester s",nativeQuery=true)
    List<DropdownListResponse> semesterDropdownList();
}

