package com.observationclass.controller;

import com.observationclass.model.ApiResponse;
import com.observationclass.model.request.AccountRequest;
import com.observationclass.service.AccountService;
import com.observationclass.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse> getListAdmin() {
        return ResponseEntity.ok().body(adminService.getListAccount());
    }

    @PostMapping("/createOrUpdate")
    public ResponseEntity<ApiResponse> createOrUpdateAccount(@RequestBody @Valid AccountRequest accountRequest) {
        return ResponseEntity.ok().body(adminService.updateAccount(accountRequest));
    }
}
