package com.observationclass.repository;

import com.observationclass.entity.Department;
import com.observationclass.model.response.DropdownListResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department,Integer> {
    @Query(value = "SELECT d.id as value,d.department_name as name\n" +
            "FROM department d",nativeQuery = true)
    List<DropdownListResponse> campusDropdownList();
}
