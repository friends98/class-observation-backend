package com.observationclass.repository;

import com.observationclass.entity.Subject;
import com.observationclass.model.response.DropdownListResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {
    Optional<Subject> findByIdAndCampusId(Integer id, Integer campusId);
    @Query(value = "SELECT s.id as value,s.subject_name as name FROM subject s WHERE s.campus_id =:campusId AND " +
            "LOWER (s.subject_name)" +
            "like LOWER (Concat('%',:subName,'%'))", nativeQuery = true)
    List<DropdownListResponse> findAllAndCampusId(Integer campusId,String subName);

}
