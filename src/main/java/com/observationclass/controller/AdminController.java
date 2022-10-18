package com.observationclass.controller;

import com.observationclass.model.ApiResponse;
import com.observationclass.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/list")

    public ResponseEntity<ApiResponse> getListAdmin() {
        return ResponseEntity.ok().body(adminService.getListAccount());
    }
}
