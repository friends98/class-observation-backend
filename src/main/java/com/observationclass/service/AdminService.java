package com.observationclass.service;

import com.observationclass.common.Constants;
import com.observationclass.entity.Account;
import com.observationclass.entity.Role;
import com.observationclass.model.ApiResponse;
import com.observationclass.model.request.AccountRequest;
import com.observationclass.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service

public class AdminService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleService roleService;

    public ApiResponse getListAccount() {
        return new ApiResponse(Constants.HTTP_CODE_200,Constants.CREATE_SUCCESS,accountRepository.findAllByDeleteFlag(Constants.DELETE_NONE));
    }
    public ApiResponse updateAccount(AccountRequest accountRequest){
        String email =accountRequest.getEmail();
        Account account =accountRepository.findByEmailAndDeleteFlag(email,Constants.DELETE_NONE).get();
        setAccount(account,accountRequest);
        account.setUpdate();
        accountRepository.save(account);
        return new ApiResponse(Constants.HTTP_CODE_200,Constants.CREATE_SUCCESS,null);
    }
    public void setAccount(Account account, AccountRequest accountRequest){
        account.setUserName(accountRequest.getUserName());
        account.setEmail(accountRequest.getEmail());
        account.getRoles().addAll(accountRequest.getRoles().stream().map(r->{
            Role role =roleService.findRoleById(r.getId());
            role.getAccounts().add(account);
            return role;
        }).collect(Collectors.toList()));
    }
}

