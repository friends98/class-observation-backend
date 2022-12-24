package com.observationclass.controller;

import com.observationclass.model.ApiResponse;
import com.observationclass.model.response.DropdownListResponse;
import com.observationclass.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @GetMapping("/subject-dropdown-list")
    public List<DropdownListResponse> subjectDropdownList(@RequestParam(name = "id") Integer accountId
            , @RequestParam(name = "code") String subName) {
        return subjectService.getSubjectDropdownList(accountId, subName);
    }

    @GetMapping("/subject-list-campus")
    public ResponseEntity<ApiResponse> subjectListCampus(@RequestParam(name = "campusId") Integer campusId) {
        return ResponseEntity.ok().body(subjectService.listAllSubjectByCampus(campusId));
    }
}
