package com.observationclass.controller;


import com.observationclass.model.response.DropdownListResponse;
import com.observationclass.service.SemesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class SemesterController {
    @Autowired
    private SemesterService semesterService;

    @GetMapping("/semesterDropdownList")
    public List<DropdownListResponse> getSemesterDropdownList(){return semesterService.getSemesterDropdownList();}
}
