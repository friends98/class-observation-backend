package com.observationclass.service;

import com.observationclass.common.Constants;
import com.observationclass.entity.*;
import com.observationclass.exception.RecordNotFoundException;
import com.observationclass.model.ApiResponse;
import com.observationclass.model.request.AccountRequest;
import com.observationclass.model.response.AccountResponse;
import com.observationclass.repository.*;
import com.observationclass.repository.dao.AccountDao;
import com.observationclass.utils.ExcelHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.ByteArrayInputStream;
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
    private SemesterRepository semesterRepository;

    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private SlotRepository slotRepository;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AccountService accountService;

    public ByteArrayInputStream exportCampus() throws IOException {
        List<Campus> listOfCampus = campusRepository.findAll();
        List<Semester> listOfSemester = semesterRepository.findAll();
        List<Subject> listOfSubject = subjectRepository.findAll();
        List<Room> listOfRoom = roomRepository.findAll();
        List<Slot> listOfSlot = slotRepository.findAll();
        ByteArrayInputStream inputStream = ExcelHelper.exportData(listOfCampus, listOfSemester, listOfRoom,
                listOfSubject, listOfSlot);

        return inputStream;
    }

//    public ByteArrayInputStream exportSemester() throws IOException {
//        List<Semester> semesterList = semesterRepository.findAll();
//        ByteArrayInputStream inputStream = ExcelHelper.writeSemesterToExcel(semesterList);
//
//        return inputStream;
//    }

    public ApiResponse uploadCampus(MultipartFile file) throws IOException {
        List<Campus> listOfCampus = ExcelHelper.getCampusDataExcel(file.getInputStream());
        if (listOfCampus.size() == 0) {
            return new ApiResponse(Constants.HTTP_CODE_400, Constants.RECORD_DOES_NOT_EXIST, null);
        }
        campusRepository.saveAll(listOfCampus);
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.CREATE_SUCCESS, null);
    }

    public ApiResponse uploadSemester(MultipartFile file) throws IOException {
        List<Semester> listOfSemester = ExcelHelper.getSemesterDataExcel(file.getInputStream());
        if (listOfSemester.size() == 0) {
            return new ApiResponse(Constants.HTTP_CODE_400, Constants.RECORD_DOES_NOT_EXIST, null);
        }
        semesterRepository.saveAll(listOfSemester);
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.CREATE_SUCCESS, null);
    }

    public ApiResponse uploadSubject(MultipartFile file) throws IOException {
        List<Subject> listOfSubject = ExcelHelper.getSubjectDataExcel(file.getInputStream());
        if (listOfSubject.size() == 0) {
            return new ApiResponse(Constants.HTTP_CODE_400, Constants.RECORD_DOES_NOT_EXIST, null);
        }
        subjectRepository.saveAll(listOfSubject);
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.CREATE_SUCCESS, null);
    }

    public ApiResponse uploadRoom(MultipartFile file) throws IOException {
        List<Room> listOfRoom = ExcelHelper.getRoomDataExcel(file.getInputStream());
        if (listOfRoom.size() == 0) {
            return new ApiResponse(Constants.HTTP_CODE_400, Constants.RECORD_DOES_NOT_EXIST, null);
        }
        roomRepository.saveAll(listOfRoom);
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.CREATE_SUCCESS, null);
    }

    public ApiResponse uploadSlot(MultipartFile file) throws IOException {
        List<Slot> listOfSlot = ExcelHelper.getSlotDataExcel(file.getInputStream());
        if (listOfSlot.size() == 0) {
            return new ApiResponse(Constants.HTTP_CODE_400, Constants.RECORD_DOES_NOT_EXIST, null);
        }
        slotRepository.saveAll(listOfSlot);
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.CREATE_SUCCESS, null);
    }


    public ApiResponse getListAccount() {
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.CREATE_SUCCESS, accountRepository.findAllByDeleteFlag(Constants.DELETE_NONE));
    }

    public ApiResponse getAccountByRole(Integer roleId, String emailSearch) {
        List<Object> listAccountByRole = accountDao.listAccountByRole(roleId, emailSearch);
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
        if (accountService.checkEmailExist(email)) {
            Optional<Account> optionalAccount=accountRepository.findByEmail(email);
            optionalAccount.get().setUserName(accountRequest.getUserName());
            optionalAccount.get().setCampusId(accountRequest.getCampusId());
            optionalAccount.get().setDepartmentId(accountRequest.getDepartmentId());
            List<Role> listOfRole = new ArrayList<>();
            for (Role r : accountRequest.getRoles()) {
                listOfRole.add(r);
            }
            optionalAccount.get().setRoles(new HashSet<>(listOfRole));
            System.out.println(listOfRole.size());
            optionalAccount.get().setDeleteFlag(Constants.DELETE_NONE);
            accountRepository.save(optionalAccount.get());
            return new ApiResponse(Constants.HTTP_CODE_200, Constants.CREATE_SUCCESS, null);
        }

        //if (!accountService.checkEmailExist(email)) {
            setAccount(account, accountRequest);
            account.setCreate();
        //}
        accountRepository.save(account);
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.CREATE_SUCCESS, null);
    }


    public ApiResponse updateAccount(AccountRequest accountRequest) {
        Optional<Account> opAccount = accountRepository.findByIdAndDeleteFlag(accountRequest.getId(), Constants.DELETE_NONE);
        if (opAccount.isEmpty()) {
            throw new RecordNotFoundException(Constants.RECORD_DOES_NOT_EXIST);
        }
        Account account = opAccount.get();
        account.setUserName(accountRequest.getUserName());
        account.setEmail(accountRequest.getEmail());
        account.setCampusId(accountRequest.getCampusId());
        account.setDepartmentId(accountRequest.getDepartmentId());
        List<Role> listOfRole = new ArrayList<>();
        for (Role r : accountRequest.getRoles()) {
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

