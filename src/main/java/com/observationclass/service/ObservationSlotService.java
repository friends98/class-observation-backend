package com.observationclass.service;

import com.observationclass.common.Constants;
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

    public ApiResponse listObservationSlotBySemester(Integer semesterId,Integer accountId){
        List<Object> listObservationSlot = observationSlotDao.listObservationSlotBySemester(semesterId, accountId);
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.SUCCESS, listObservationSlot);
    }

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
