package com.observationclass.service;

import com.observationclass.model.response.DropdownListResponse;
import com.observationclass.repository.SemesterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SemesterService {
    @Autowired
    private SemesterRepository semesterRepository;

    public List<DropdownListResponse> getSemesterDropdownList() {
        return semesterRepository.semesterDropdownList();
    }
}
