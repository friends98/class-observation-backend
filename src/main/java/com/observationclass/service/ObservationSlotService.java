package com.observationclass.service;

import com.observationclass.common.Constants;
import com.observationclass.entity.ObservationPlan;
import com.observationclass.entity.ObservationSlot;
import com.observationclass.exception.RecordNotFoundException;
import com.observationclass.model.ApiResponse;
import com.observationclass.model.request.ObservationSlotRequest;
import com.observationclass.repository.*;
import com.observationclass.repository.dao.ObservationSlotDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ObservationSlotService {
    @Autowired
    private ObservationSlotDao observationSlotDao;

    @Autowired
    private ObservationSlotRepository observationSlotRepository;
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
    private ObservationPlanRepository observationPlanRepository;

    public ApiResponse createNewSlot(ObservationSlotRequest observationSlotRequest,Integer planId){
        Optional<ObservationPlan> opObservationPlan = observationPlanRepository.findById(planId);
        if(opObservationPlan.isEmpty()){
            throw new RecordNotFoundException("Not found optional Observation Plan");
        }
        ObservationSlot observationSlot = new ObservationSlot();
        observationSlot.setAccount(accountRepository.findById(observationSlotRequest.getAccountId()).get());
        observationSlot.setSubject(subjectRepository.findById(observationSlotRequest.getSubjectId()).get());
        observationSlot.setReason(observationSlotRequest.getReason());
        observationSlot.setSlotTime(observationSlotRequest.getSlotTime());
        observationSlot.setSlot(slotRepository.findById(observationSlotRequest.getSlotId()).get());
        observationSlot.setRoom(roomRepository.findById(observationSlotRequest.getRoomId()).get());
        observationSlot.setClassName(observationSlotRequest.getClassName());
        observationSlot.setHeadTraining(accountRepository.findById(observationSlotRequest.getHeadTraining()).get());
        observationSlot.setHeadSubject(accountRepository.findById(observationSlotRequest.getHeadSubject()).get());
        observationSlot.setAccount1(accountRepository.findById(observationSlotRequest.getAccountId1()).get());
        observationSlot.setAccount2(accountRepository.findById(observationSlotRequest.getAccountId2()).get());
        observationSlot.setObservationPlan(opObservationPlan.get());
        observationSlot.setCreate();
        observationSlotRepository.save(observationSlot);
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.CREATE_SUCCESS, null);
    }

    // danh sach slot theo ke hoach
    public ApiResponse listOfObservationSlotByPlanId(Integer planId) {
        List<Object> listOfObservationSlot = observationSlotDao.listOfObservationSlotByPlanId(planId);
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.SUCCESS, listOfObservationSlot);
    }
    // hiên thi ket qua danh gia của GV đi dự giờ theo  slot id
    public ApiResponse resultObservationSlotById(Integer observationSlotId){
        List<Object> listResultObservationSlot = observationSlotDao.resultObservationSlot(observationSlotId);
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.SUCCESS, listResultObservationSlot);
    }
    // đánh giá slot đó đạt hay ko đạt
    public ApiResponse rejectResultObservationSlot(Integer observationSlotId){
        Optional<ObservationSlot> opObservationSlot = observationSlotRepository.findByIdAndDeleteFlag(observationSlotId,
                Constants.DELETE_NONE);
        opObservationSlot.get().setResult(Constants.REJECT);
        observationSlotRepository.save(opObservationSlot.get());
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.UPDATE_SUCCESS, null);

    }
    // danh sach cac slot cua CNBM theo kì
    public ApiResponse listObservationSlotBySemester(Integer semesterId,Integer accountId){
        List<Object> listObservationSlot = observationSlotDao.listObservationSlotBySemester(semesterId, accountId);
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.SUCCESS, listObservationSlot);
    }

    //update slot
    public ApiResponse updateObservationSlot(ObservationSlotRequest observationSlotRequest){
        Optional<ObservationSlot> opObservationSlot = observationSlotRepository.findByIdAndDeleteFlag(observationSlotRequest.getId(), Constants.DELETE_NONE);
        if(opObservationSlot.isEmpty()){
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
        //opObservationSlot.get().setHeadSubject(accountRepository.findById(observationSlotRequest.getHeadSubject()).get());
        opObservationSlot.get().setAccount1(accountRepository.findById(observationSlotRequest.getAccountId1()).get());
        opObservationSlot.get().setAccount2(accountRepository.findById(observationSlotRequest.getAccountId2()).get());
        opObservationSlot.get().setUpdate();
        observationSlotRepository.save(opObservationSlot.get());
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.UPDATE_SUCCESS, null);
    }

}
