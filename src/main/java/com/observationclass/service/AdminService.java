package com.observationclass.service;

import com.observationclass.common.Constants;
import com.observationclass.entity.Account;
import com.observationclass.entity.Campus;
import com.observationclass.entity.Role;
import com.observationclass.exception.RecordNotFoundException;
import com.observationclass.model.ApiResponse;
import com.observationclass.model.request.AccountRequest;
import com.observationclass.model.response.AccountResponse;
import com.observationclass.repository.AccountRepository;
import com.observationclass.repository.CampusRepository;
import com.observationclass.repository.dao.AccountDao;
import com.observationclass.utils.ExcelHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

import java.util.*;
import java.util.stream.Collectors;

@Service

public class AdminService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CampusRepository campusRepository;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AccountService accountService;


    public ApiResponse uploadCampus(MultipartFile file) throws IOException {
        List<Campus> listOfCampus = ExcelHelper.getCampusDataExcel(file.getInputStream());
       // System.out.println(listOfCampus.size()+"sizeeeeeee");
        campusRepository.saveAll(listOfCampus);
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.CREATE_SUCCESS, null);
    }

    public ApiResponse getListAccount() {
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.CREATE_SUCCESS, accountRepository.findAllByDeleteFlag(Constants.DELETE_NONE));
    }

    public ApiResponse getAccountByRole(Integer roleId,String emailSearch) {
        List<Object> listAccountByRole = accountDao.listAccountByRole(roleId,emailSearch);
        if (listAccountByRole.isEmpty()) {

        }

        return new ApiResponse(Constants.HTTP_CODE_200, Constants.SUCCESS, listAccountByRole);
    }

    public ApiResponse deleteAccountById(Integer id) {
        Optional<Account> opAccount = accountRepository.findByIdAndDeleteFlag(id, Constants.DELETE_NONE);
        if (opAccount.isEmpty()) {
            throw new RecordNotFoundException(Constants.RECORD_DOES_NOT_EXIST);
        }
        opAccount.get().setDeleteFlag(Constants.DELETE_TRUE);
        accountRepository.save(opAccount.get());
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.DELETE_SUCCESS, null);
    }

    public ApiResponse addNewAccount(AccountRequest accountRequest) {
        Account account = new Account();
        String email = accountRequest.getEmail();
        if (!accountService.checkEmailExist(email)) {
            setAccount(account, accountRequest);
            account.setCreate();
        }
        accountRepository.save(account);
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.CREATE_SUCCESS, null);
    }


    public ApiResponse updateAccount(AccountRequest accountRequest) {
        Optional<Account> opAccount = accountRepository.findByIdAndDeleteFlag(accountRequest.getId(), Constants.DELETE_NONE);
        if (opAccount.isEmpty()) {
            throw new RecordNotFoundException(Constants.RECORD_DOES_NOT_EXIST);
        }
        Account account =opAccount.get();
        account.setUserName(accountRequest.getUserName());
        account.setEmail(accountRequest.getEmail());
        account.setCampusId(accountRequest.getCampusId());
        account.setDepartmentId(accountRequest.getDepartmentId());
        List<Role> listOfRole = new ArrayList<>();
        for(Role  r:accountRequest.getRoles()){
            listOfRole.add(r);
        }
        account.setRoles(new HashSet<>(listOfRole));
        opAccount.get().setUpdate();
        accountRepository.save(opAccount.get());
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.UPDATE_SUCCESS, null);
    }

    public void setAccount(Account account, AccountRequest accountRequest) {
        account.setUserName(accountRequest.getUserName());
        account.setEmail(accountRequest.getEmail());
        account.setCampusId(accountRequest.getCampusId());
        account.setDepartmentId(accountRequest.getDepartmentId());
        account.getRoles().addAll(accountRequest.getRoles().stream().map(r -> {
            Role role = roleService.findRoleById(r.getId());
            role.getAccounts().add(account);
            return role;
        }).collect(Collectors.toList()));
    }
}

