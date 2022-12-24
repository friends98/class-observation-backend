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
    //Optional<Subject> findByIdAndAndDepartmentId(Integer id, Integer campusId);
    @Query(value = "SELECT s.id as value,s.subject_name as name FROM subject s WHERE s.department_id =:departmentId " +
            "AND " +
            "LOWER (s.subject_name)" +
            "like LOWER (Concat('%',:subName,'%'))", nativeQuery = true)
    List<DropdownListResponse> findAllAndAndDepartmentId(Integer departmentId,String subName);
        @Query(value="SELECT *\n" +
            "FROM subject s \n" +
            "LEFT JOIN department d ON s.department_id =d.id \n" +
            "WHERE d.campus_id =:campusId",nativeQuery = true)
    List<Subject> findAllByCampus(Integer campusId);

}
