package com.observationclass.service;

import com.observationclass.common.Constants;
import com.observationclass.exception.RecordNotFoundException;
import com.observationclass.model.ApiResponse;
import com.observationclass.model.response.DropdownListResponse;
import com.observationclass.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public ApiResponse getDeparmentDropdownList(Integer campusId, String depName){
        List<DropdownListResponse> listDepartmentByCampus=departmentRepository.campusDropdownList(campusId,depName);

        return new ApiResponse(Constants.HTTP_CODE_200,Constants.SUCCESS,listDepartmentByCampus);
    }

}
