package com.observationclass.controller;

import com.observationclass.model.ApiResponse;
import com.observationclass.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/list-account")
    public ResponseEntity<ApiResponse> listAccountByCampus(@RequestParam(name = "id") Integer campusId
            , @RequestParam(name = "email") String email) {
        return ResponseEntity.ok().body(accountService.listAccountByCampus(campusId, email));
    }
}
