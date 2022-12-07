package com.observationclass.service;

import com.observationclass.common.Constants;
import com.observationclass.entity.*;
import com.observationclass.exception.RecordNotFoundException;
import com.observationclass.model.ApiResponse;
import com.observationclass.model.request.ObservationPlanRequest;
import com.observationclass.model.request.ObservationPlanUpdateRequest;
import com.observationclass.model.request.ObservationSlotRequest;
import com.observationclass.repository.*;
import com.observationclass.repository.dao.ObservationPlanDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private SlotRepository slotRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private ObservationSlotRepository observationSlotRepository;

    @Autowired
    ObservationPlanDao observationPlanDao;
    public ApiResponse getStatusPlanById(Integer planId) {
        Optional<ObservationPlan> opObservationPlan=observationPlanRepository.findByIdAndDeleteFlag(planId,Constants.DELETE_NONE);
        if(opObservationPlan.isPresent()){
            return new ApiResponse(Constants.HTTP_CODE_200, Constants.SUCCESS, opObservationPlan.get().getPlanStatus());
        }
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.SUCCESS, 0);
    }

    public ApiResponse approveObservationPlan(Integer planId,Integer status){
        Optional<ObservationPlan> opObservationPlan = observationPlanRepository.
                findByIdAndPlanStatusAndDeleteFlag(planId, Constants.NEW_PLAN, Constants.DELETE_NONE);
        if (opObservationPlan.isEmpty()) {
            throw new RecordNotFoundException("Optinal observation not found!");
        }
        opObservationPlan.get().setPlanStatus(status);
        observationPlanRepository.save(opObservationPlan.get());
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.UPDATE_SUCCESS, null);
    }

    public ApiResponse listPlanBySemesterAndCampus(Integer campusId, Integer semesterId) {
        List<Object> listSearchObservationPlan = observationPlanDao
                .listSearchObservationPlan(campusId,semesterId);
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.SUCCESS, listSearchObservationPlan);
    }

    public ApiResponse createNewObservationPlan(ObservationPlanRequest observationPlanRequest) {

        ObservationPlan observationPlan = new ObservationPlan();
        Optional<Campus> opCampus = campusRepository.findById(observationPlanRequest.getCampusId());
        Optional<Semester> opSemester = semesterRepository.findById(observationPlanRequest.getSemesterId());
        Integer departmentId = getDepartmentByAccount(observationPlanRequest.getAccountId());
        Optional<Department> opDepartment = departmentRepository.findByIdAndCampusId(departmentId, observationPlanRequest.getCampusId());
        
        if (opCampus.isEmpty() || opSemester.isEmpty() || opDepartment.isEmpty()) {
            throw new RecordNotFoundException(Constants.RECORD_DOES_NOT_EXIST);
        }
        observationPlan.setSemester(opSemester.get());
        observationPlan.setCampus(opCampus.get());
        observationPlan.setDepartment(opDepartment.get());
        observationPlan.setPlanStatus(observationPlanRequest.getPlanStatus() == null ? Constants.NEW_PLAN
                : observationPlanRequest.getPlanStatus());
        observationPlanRepository.save(observationPlan);
        List<ObservationSlot> lstObservationSlot = new ArrayList<>();
        Integer campusId = observationPlanRequest.getCampusId();
        observationPlanRequest.getObservationSlotsRequest().forEach(r -> {
            ObservationSlot observationSlot = new ObservationSlot();
            observationSlot.setAccount(getAccountById(r.getAccountId(), campusId, Constants.DELETE_NONE));
            observationSlot.setSubject(getSubjectById(r.getSubjectId()));
            observationSlot.setReason(r.getReason());
            observationSlot.setSlotTime(r.getSlotTime());
            observationSlot.setSlot(getSlotById(r.getSlotId()));
            observationSlot.setRoom(getRoomById(r.getRoomId()));
            observationSlot.setClassName(r.getClassName());
            observationSlot.setHeadTraining(getAccountById(r.getHeadTraining(), campusId, Constants.DELETE_NONE));
            observationSlot.setHeadSubject(getAccountById(r.getHeadSubject(), campusId, Constants.DELETE_NONE));
            observationSlot.setAccount1(getAccountById(r.getAccountId1(), campusId, Constants.DELETE_NONE));
            observationSlot.setAccount2(getAccountById(r.getAccountId2(), campusId, Constants.DELETE_NONE));
            observationSlot.setObservationPlan(observationPlan);
            observationSlot.setResult(0);
            lstObservationSlot.add(observationSlot);
        });
        Collections.reverse(lstObservationSlot);
        observationSlotRepository.saveAll(lstObservationSlot);
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.CREATE_SUCCESS, observationPlan.getId());
    }


    public ApiResponse updateOnlyObservationPlan(ObservationPlanUpdateRequest observationPlanUpdateRequest) {
        Optional<ObservationPlan> opObservationPlan = observationPlanRepository
                .findByIdAndPlanStatusAndDeleteFlag(observationPlanUpdateRequest
                        .getId(), Constants.REJECT, Constants.DELETE_NONE);
        Optional<Semester> opSemester = semesterRepository.findById(observationPlanUpdateRequest.getSemesterId());
        Optional<Department> opDepartment = departmentRepository.findById(observationPlanUpdateRequest.getDepartmentId());
        Optional<Campus> opCampus = campusRepository.findById(observationPlanUpdateRequest.getCampusId());
        if (opObservationPlan.isEmpty()) {
            throw new RecordNotFoundException(Constants.RECORD_DOES_NOT_EXIST);
        }
        if (opSemester.isEmpty() || opDepartment.isEmpty() || opCampus.isEmpty()) {
            throw new RecordNotFoundException(Constants.RECORD_DOES_NOT_EXIST);
        }
        opObservationPlan.get().setCampus(opCampus.get());
        opObservationPlan.get().setDepartment(opDepartment.get());
        opObservationPlan.get().setSemester(opSemester.get());
        opObservationPlan.get().setPlanStatus(Constants.NEW_PLAN);
        opObservationPlan.get().setUpdate();
        observationPlanRepository.save(opObservationPlan.get());
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.UPDATE_SUCCESS, null);
    }

    public ApiResponse updateOnlyObservationSlot(ObservationSlotRequest observationSlotRequest) {
        Optional<ObservationSlot> opObservationSlot = observationSlotRepository
                .findByIdAndDeleteFlag(observationSlotRequest.getId(), Constants.DELETE_NONE);
        if (opObservationSlot.isEmpty()) {
            throw new RecordNotFoundException(Constants.RECORD_DOES_NOT_EXIST);
        }
        opObservationSlot.get().setAccount(accountRepository.findById(observationSlotRequest.getAccountId()).get());
        opObservationSlot.get().setSubject(subjectRepository.findById(observationSlotRequest.getSubjectId()).get());
        opObservationSlot.get().setReason(observationSlotRequest.getReason());
        opObservationSlot.get().setSlotTime(observationSlotRequest.getSlotTime());
        opObservationSlot.get().setSlot(slotRepository.findById(observationSlotRequest.getSlotId()).get());
        opObservationSlot.get().setRoom(roomRepository.findById(observationSlotRequest.getRoomId()).get());
        opObservationSlot.get().setClassName(observationSlotRequest.getClassName());
        opObservationSlot.get().setHeadTraining(accountRepository.findById(observationSlotRequest.getHeadTraining()).get());
        opObservationSlot.get().setHeadSubject(accountRepository.findById(observationSlotRequest.getHeadSubject()).get());
        opObservationSlot.get().setAccount1(accountRepository.findById(observationSlotRequest.getAccountId1()).get());
        opObservationSlot.get().setAccount2(accountRepository.findById(observationSlotRequest.getAccountId2()).get());
        opObservationSlot.get().setUpdate();
        observationSlotRepository.save(opObservationSlot.get());
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.UPDATE_SUCCESS, null);

    }

    public ApiResponse deleteObservationPlan(Integer id) {
        Optional<ObservationPlan> opObservationPlan = observationPlanRepository.findById(id);
        if (opObservationPlan.isPresent()) {
            opObservationPlan.get().setDeleteFlag(Constants.DELETE_TRUE);
        }
        observationPlanRepository.save(opObservationPlan.get());
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.DELETE_SUCCESS, null);

    }

    public Account getAccountById(Integer id, Integer campusId, Integer deleteFlag) {
        return accountRepository.findByIdAndCampusIdAndDeleteFlag(id, campusId, deleteFlag).get();
    }

    public Subject getSubjectById(Integer id) {
        return subjectRepository.findById(id).get();
    }

    public Slot getSlotById(Integer id) {
        return slotRepository.findById(id).get();
    }

    public Room getRoomById(Integer id) {
        return roomRepository.findById(id).get();
    }

    public Integer getDepartmentByAccount(Integer accountId){
        Optional<Account> opAccount=accountRepository.findById(accountId);
        if(!opAccount.isPresent()){
            throw  new RecordNotFoundException("Account not found");
        }
        return opAccount.get().getDepartmentId();
    }
}

