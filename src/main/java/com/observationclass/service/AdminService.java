package com.observationclass.service;

import com.observationclass.common.Constants;
import com.observationclass.model.ApiResponse;
import com.observationclass.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminService {
    @Autowired
    private AccountRepository accountRepository;

    public ApiResponse getListAccount() {
        return new ApiResponse(Constants.HTTP_CODE_200,Constants.SUCCESS,accountRepository.findAll());
    }
}

