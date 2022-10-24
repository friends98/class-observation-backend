package com.observationclass.controller;


import com.observationclass.model.ApiResponse;
import com.observationclass.model.request.CriteriaRequest;
import com.observationclass.service.CriteriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/admin")
public class CriteriaController {
    @Autowired
    private CriteriaService criteriaService;

    @PostMapping("/createCriteria")
    public ResponseEntity<ApiResponse> createNewCriteria(@RequestBody @Valid CriteriaRequest criteriaRequest) {
        return ResponseEntity.ok().body(criteriaService.addNewCriteria(criteriaRequest));
    }

    @PostMapping("/updateCriteria")
    public ResponseEntity<ApiResponse> updateCriteria(@RequestBody @Valid CriteriaRequest criteriaRequest) {
        return ResponseEntity.ok().body(criteriaService.updateCriteria(criteriaRequest));
    }
}
