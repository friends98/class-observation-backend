package com.observationclass.service;

import com.observationclass.common.Constants;
import com.observationclass.entity.*;
import com.observationclass.model.ApiResponse;
import com.observationclass.model.request.ObservationPlanRequest;
import com.observationclass.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class ObservationPlanService {

    @Autowired
    private ObservationPlanRepository observationPlanRepository;

    @Autowired
    private CampusRepository campusRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private SemesterRepository semesterRepository;

    public ApiResponse createObservationPlan(ObservationPlanRequest observationPlanRequest) {
        ObservationPlan observationPlan = new ObservationPlan();
        setObservationPlan(observationPlan, observationPlanRequest);
        observationPlan.setCreate();
        observationPlanRepository.save(observationPlan);
        return new ApiResponse(Constants.HTTP_CODE_200,Constants.CREATE_SUCCESS,null);

    }
    public ApiResponse updateObservationPlan(ObservationPlanRequest observationPlanRequest){
        Optional<ObservationPlan> opObservationPlan =observationPlanRepository.findById(observationPlanRequest.getId());
        if(!opObservationPlan.isEmpty()){
            Optional<Semester> opSemester =semesterRepository.findById(observationPlanRequest.getSemesterId());
            Optional<Campus> opCampus =campusRepository.findById(observationPlanRequest.getCampusId());
            Optional<Department> opDepartment =departmentRepository.findById(observationPlanRequest.getDepartmentId());
            Optional<Account> opAccount =accountRepository.findById(observationPlanRequest.getAccountId());
            if (opCampus != null && opSemester != null && opDepartment != null && opAccount != null) {
                opObservationPlan.get().setCampus(opCampus.get());
                opObservationPlan.get().setDepartment(opDepartment.get());
                opObservationPlan.get().setSemester(opSemester.get());
                opObservationPlan.get().setAccount(opAccount.get());
            }
            opObservationPlan.get().setPlanStatus(observationPlanRequest.getPlanStatus());
            opObservationPlan.get().setUpdate();
            observationPlanRepository.save(opObservationPlan.get());
        }
        return new ApiResponse(Constants.HTTP_CODE_200,Constants.UPDATE_SUCCESS,null);
    }

    public void setObservationPlan(ObservationPlan observationPlan, ObservationPlanRequest observationPlanRequest) {
        Optional<Campus> opCampus = campusRepository.findById(observationPlanRequest.getCampusId());
        Optional<Semester> opSemester = semesterRepository.findById(observationPlanRequest.getSemesterId());
        Optional<Department> opDepartment = departmentRepository.findById(observationPlanRequest.getDepartmentId());
        Optional<Account> opAccount = accountRepository.findById(observationPlanRequest.getAccountId());
        if (opCampus != null && opSemester != null && opDepartment != null && opAccount != null) {
            observationPlan.setCampus(opCampus.get());
            observationPlan.setDepartment(opDepartment.get());
            observationPlan.setSemester(opSemester.get());
            observationPlan.setAccount(opAccount.get());
            observationPlan.setPlanStatus(observationPlanRequest.getPlanStatus()==null?Constants.NEW_PLAN
                    :observationPlanRequest.getPlanStatus());
        }
    }
}

