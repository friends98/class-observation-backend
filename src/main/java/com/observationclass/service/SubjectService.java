package com.observationclass.service;

import com.observationclass.common.Constants;
import com.observationclass.entity.Account;
import com.observationclass.entity.Subject;
import com.observationclass.exception.RecordNotFoundException;
import com.observationclass.model.ApiResponse;
import com.observationclass.model.response.DropdownListResponse;
import com.observationclass.repository.AccountRepository;
import com.observationclass.repository.SubjectRepository;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private AccountRepository accountRepository;

    public ApiResponse listAllSubjectByCampus(Integer campusId) {
        List<Subject> listSubject = subjectRepository.findAllByCampus(campusId);
        if (listSubject == null) {
            throw new RecordNotFoundException(Constants.RECORD_DOES_NOT_EXIST);
        }
        return new ApiResponse(Constants.HTTP_CODE_200,Constants.SUCCESS,listSubject);
    }

    public List<DropdownListResponse> getSubjectDropdownList(Integer accountId, String subName) {
        Integer departmentId = getDepartmentIdByAccount(accountId);
        List<DropdownListResponse> listSubject = subjectRepository.findAllAndAndDepartmentId(departmentId, subName);
        if (listSubject == null) {
            throw new RecordNotFoundException(Constants.RECORD_DOES_NOT_EXIST);
        }
        return listSubject;
    }
    public  Integer getDepartmentIdByAccount(Integer accountId) {
        Optional<Account> opAccount=accountRepository.findById(accountId);
        if (!opAccount.isPresent()) {
            throw new RecordNotFoundException("Record not found");
        }
        return opAccount.get().getDepartmentId();
    }

}
