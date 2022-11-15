package com.observationclass.controller;


import com.observationclass.model.ApiResponse;
import com.observationclass.model.request.CriteriaRequest;
import com.observationclass.service.CriteriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/training")
public class CriteriaController {
    @Autowired
    private CriteriaService criteriaService;

    @GetMapping("/list-criteria-campus")
    public ResponseEntity<ApiResponse> listCriteriaByCampus() {
        return ResponseEntity.ok().body(criteriaService.getListCriteria());
    }

    @PostMapping("/create-criteria")
    public ResponseEntity<ApiResponse> createNewCriteria(@RequestBody @Valid CriteriaRequest criteriaRequest) {
        return ResponseEntity.ok().body(criteriaService.addNewCriteria(criteriaRequest));
    }

    @PostMapping("/update-criteria")
    public ResponseEntity<ApiResponse> updateCriteria(@RequestBody @Valid CriteriaRequest criteriaRequest) {
        return ResponseEntity.ok().body(criteriaService.updateCriteria(criteriaRequest));
    }

    @PostMapping("/delete-criteria")
    public ResponseEntity<ApiResponse> deleteCriteriaById(@RequestParam(name = "id") Integer id) {
        return ResponseEntity.ok().body(criteriaService.deleteCriteriaById(id));
    }
}
