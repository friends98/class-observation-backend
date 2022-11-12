package com.observationclass.controller;

import com.observationclass.model.ApiResponse;
import com.observationclass.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @GetMapping("/subject-dropdown-list")
    public ResponseEntity<ApiResponse> getListSubjectByCampusId(@RequestParam(name="id")Integer campusId
                                                                ,@RequestParam(name="code")String subCode){
        return ResponseEntity.ok().body(subjectService.getDropdownlistSubjectByCampus(campusId,subCode));

    }
}
