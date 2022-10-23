package com.observationclass.controller;

import com.observationclass.model.response.DropdownListResponse;
import com.observationclass.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/listDepartment")
    public List<DropdownListResponse> getDepartmentDropdownList() {
        return departmentService.getDeparmentDropdownList();
    }
}
