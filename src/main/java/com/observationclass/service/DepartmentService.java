package com.observationclass.service;

import com.observationclass.model.response.DropdownListResponse;
import com.observationclass.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public List<DropdownListResponse> getDeparmentDropdownList(Integer campusId, String depName){
        List<DropdownListResponse> listDepartment=departmentRepository.campusDropdownList(campusId,depName);
        return listDepartment;
    }
}
