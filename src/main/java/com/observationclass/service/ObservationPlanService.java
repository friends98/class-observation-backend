package com.observationclass.service;

import com.observationclass.common.Constants;
import com.observationclass.entity.*;
import com.observationclass.model.ApiResponse;
import com.observationclass.model.request.ObservationPlanRequest;
import com.observationclass.model.request.ObservationPlanUpdateRequest;
import com.observationclass.model.request.SearchObservationPlanRequest;
import com.observationclass.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public ApiResponse createNewObservationPlan(ObservationPlanRequest observationPlanRequest) {

        ObservationPlan observationPlan = new ObservationPlan();
            Optional<Campus> opCampus = campusRepository.findById(observationPlanRequest.getCampusId());
            Optional<Semester> opSemester = semesterRepository.findById(observationPlanRequest.getSemesterId());
            Optional<Department> opDepartment = departmentRepository.findById(observationPlanRequest.getDepartmentId());
            if(opCampus.isEmpty()||opSemester.isEmpty()||opDepartment.isEmpty()){

            }
            observationPlan.setSemester(opSemester.get());
            observationPlan.setCampus(opCampus.get());
            observationPlan.setDepartment(opDepartment.get());
            observationPlan.setPlanStatus(observationPlanRequest.getPlanStatus() == null ? Constants.NEW_PLAN
                    : observationPlanRequest.getPlanStatus());
            observationPlanRepository.save(observationPlan);
            List<ObservationSlot> lstObservationSlot = new ArrayList<>();
            observationPlanRequest.getObservationSlotsRequest().forEach(r -> {
                ObservationSlot observationSlot = new ObservationSlot();
                observationSlot.setSlot(slotRepository.findById(r.getSlotId()).get());
                observationSlot.setRoom(roomRepository.findById(r.getRoomId()).get());
                observationSlot.setSubject(subjectRepository.findById(r.getSubjectId()).get());
                observationSlot.setSlotTime(r.getSlotTime());
                observationSlot.setAccount(accountRepository.findById(r.getAccountId()).get());
                observationSlot.setAccount1(accountRepository.findById(r.getAccountId1()).get());
                observationSlot.setAccount2(accountRepository.findById(r.getAccountId2()).get());
                observationSlot.setReason(r.getReason());
                observationSlot.setObservationPlan(observationPlan);
                lstObservationSlot.add(observationSlot);
                //observationSlotRepository.save(observationSlot);
            });
            Collections.reverse(lstObservationSlot);
            observationSlotRepository.saveAll(lstObservationSlot);


        return new ApiResponse(Constants.HTTP_CODE_200, Constants.CREATE_SUCCESS, null);
    }

    public ApiResponse updateObservationPlan(ObservationPlanUpdateRequest observationPlanUpdateRequest) {
        Optional<ObservationPlan> opObservationPlan = observationPlanRepository.findById(observationPlanUpdateRequest.getId());
        Optional<Semester> optionalSemester = semesterRepository.findById(observationPlanUpdateRequest.getSemesterId());
        Optional<Department> opDepartment =departmentRepository.findById(observationPlanUpdateRequest.getDepartmentId());
        Optional<Campus> opCampus = campusRepository.findById(observationPlanUpdateRequest.getCampusId());
        // loi khong tim thay id
        if(opObservationPlan.get().getPlanStatus()==0 || opObservationPlan.get().getPlanStatus()==2){
            opObservationPlan.get().setSemester(optionalSemester.get());
            opObservationPlan.get().setDepartment(opDepartment.get());
            opObservationPlan.get().setCampus(opCampus.get());
        }
        observationPlanRepository.save(opObservationPlan.get());
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.UPDATE_SUCCESS, null);
    }
    public ApiResponse deleteObservationPlan(Integer id){
        Optional<ObservationPlan> opObservationPlan = observationPlanRepository.findById(id);
        if(opObservationPlan.isPresent()){
            opObservationPlan.get().setDeleteFlag(Constants.DELETE_TRUE);
        }
        observationPlanRepository.save(opObservationPlan.get());
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.DELETE_SUCCESS, null);

    }
    public ApiResponse searchObservationPlanByCampus(SearchObservationPlanRequest searchObservationPlanRequest){

        return new ApiResponse(Constants.HTTP_CODE_200,Constants.SUCCESS,null);
    }

   /* public ApiResponse updateObservationPlan(ObservationPlanRequest observationPlanRequest){
        Optional<ObservationPlan> opObservationPlan =observationPlanRepository.findByIdAndPlanStatus(observationPlanRequest.getId(),
                2);
        try {
            Optional<Campus> opCampus = campusRepository.findById(observationPlanRequest.getCampusId());
            Optional<Semester> opSemester = semesterRepository.findById(observationPlanRequest.getSemesterId());
            Optional<Department> opDepartment = departmentRepository.findById(observationPlanRequest.getDepartmentId());
            opObservationPlan.get().setSemester(opSemester.get());
            opObservationPlan.get().setCampus(opCampus.get());
            opObservationPlan.get().setDepartment(opDepartment.get());
            opObservationPlan.get().setPlanStatus(observationPlanRequest.getPlanStatus() == null ? Constants.NEW_PLAN
                    : observationPlanRequest.getPlanStatus());
            observationPlanRequest.getObservationSlotsRequest().forEach(r -> {
                ObservationSlot observationSlot = observationSlotRepository.findById(r.getId()).get();
                observationSlot.setSlot(slotRepository.findById(r.getSlotId()).get());
                observationSlot.setRoom(roomRepository.findById(r.getRoomId()).get());
                observationSlot.setSubject(subjectRepository.findById(r.getSubjectId()).get());
                observationSlot.setSlotTime(r.getSlotTime());
                observationSlot.setAccount(accountRepository.findById(r.getAccountId()).get());
                observationSlot.setAccount1(accountRepository.findById(r.getAccountId1()).get());
                observationSlot.setAccount2(accountRepository.findById(r.getAccountId2()).get());
                observationSlot.setReason(r.getReason());
                observationSlot.setObservationPlan(opObservationPlan.get());
                observationSlotRepository.save(observationSlot);
            });
            observationPlanRepository.save(opObservationPlan.get());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.UPDATE_SUCCESS, null);
    }*/
}

