package com.observationclass.service;

import com.observationclass.common.Constants;
import com.observationclass.entity.Semester;
import com.observationclass.exception.RecordNotFoundException;
import com.observationclass.model.ApiResponse;
import com.observationclass.model.response.DropdownListResponse;
import com.observationclass.repository.SemesterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SemesterService {
    @Autowired
    private SemesterRepository semesterRepository;

    public List<DropdownListResponse> getSemesterDropdownList() {
        return semesterRepository.semesterDropdownList();
    }

    public ApiResponse getCurrentSemesterId(){
        Optional<Semester> opSemester =semesterRepository.findCurrentSemester();
        if(!opSemester.isPresent()){
            throw new RecordNotFoundException("Record not found");
        }
        Integer semsterId =opSemester.get().getId();
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.SUCCESS, semsterId);
    }
}
