package com.observationclass.controller;

import com.observationclass.model.response.DropdownListResponse;
import com.observationclass.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/list-department")
    public List<DropdownListResponse> getDepartmentDropdownList(@RequestParam(name="id")Integer campusId) {
        return departmentService.getDeparmentDropdownList(campusId);
    }
}
