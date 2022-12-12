package com.observationclass.service;

import com.observationclass.common.Constants;
import com.observationclass.exception.RecordNotFoundException;
import com.observationclass.model.response.DropdownListResponse;
import com.observationclass.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;

    public List<DropdownListResponse> getSubjectDropdownList(Integer campusId, String subName) {
        List<DropdownListResponse> listSubject = subjectRepository.findAllAndCampusId(campusId, subName);
        if (listSubject == null) {
            throw new RecordNotFoundException(Constants.RECORD_DOES_NOT_EXIST);
        }
        return listSubject;
    }
}
