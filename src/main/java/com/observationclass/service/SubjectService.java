package com.observationclass.service;

import com.observationclass.common.Constants;
import com.observationclass.exception.RecordNotFoundException;
import com.observationclass.model.ApiResponse;
import com.observationclass.model.response.DropdownListResponse;
import com.observationclass.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.scanner.Constant;

import java.util.List;

@Service
public class SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;


    public ApiResponse getDropdownlistSubjectByCampus(Integer campusId,String subCode){
        List<DropdownListResponse> listSubjectByCampus = subjectRepository.findAllAndCampusId(campusId, subCode);
        if(listSubjectByCampus==null){
            throw new RecordNotFoundException(Constants.RECORD_DOES_NOT_EXIST);
        }
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.SUCCESS, listSubjectByCampus);
    }

}
