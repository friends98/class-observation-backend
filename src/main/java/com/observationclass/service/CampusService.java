package com.observationclass.service;

import com.observationclass.model.response.DropdownListResponse;
import com.observationclass.repository.CampusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampusService {
    @Autowired
    private CampusRepository campusRepository;

    public List<DropdownListResponse> getCampusDropdownList() {
        return campusRepository.campusDropdownList();
    }
}
