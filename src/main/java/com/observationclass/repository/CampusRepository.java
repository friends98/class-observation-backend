package com.observationclass.repository;

import com.observationclass.entity.Campus;
import com.observationclass.model.response.DropdownListResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampusRepository  extends JpaRepository<Campus,Integer> {
    @Query(value = "SELECT c.id as value,c.campus_name as name\n" +
            "FROM campus c",nativeQuery = true)
    List<DropdownListResponse> campusDropdownList();
}

