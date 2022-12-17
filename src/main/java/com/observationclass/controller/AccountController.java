package com.observationclass.controller;

import com.observationclass.model.ApiResponse;
import com.observationclass.model.response.DropdownListResponse;
import com.observationclass.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/list-account")
    public List<DropdownListResponse> listAccountByCampus(@RequestParam(name = "id") Integer campusId
            , @RequestParam(name = "email") String email) {
        return accountService.listAccountByCampus(campusId, email);
    }

    @GetMapping("/list-account-lecture")
    public List<DropdownListResponse> listAccountByCampusAndRole(@RequestParam(name = "id") Integer campusId,
                                                                 @RequestParam(name = "email")String email) {
        return accountService.listAccountByCampusAndRole(campusId, email);
    }


}
