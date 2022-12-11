package com.observationclass.service;

import com.observationclass.common.Constants;
import com.observationclass.entity.ObservationDetail;
import com.observationclass.entity.ObservationPlan;
import com.observationclass.entity.ObservationReview;
import com.observationclass.entity.ObservationSlot;
import com.observationclass.exception.RecordNotFoundException;
import com.observationclass.model.ApiResponse;
import com.observationclass.model.request.ObservationSlotRequest;
import com.observationclass.model.response.EvaluationObservationReivewDetail;
import com.observationclass.repository.*;
import com.observationclass.repository.dao.ObservationSlotDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Autowired
    private ObservationReviewRepository observationReviewRepository;

    @Autowired
    private ObservationDetailRepository observationDetailRepository;
    public ApiResponse getResultSlot(Integer slotId){
        Optional<ObservationSlot> opObservationSlot = observationSlotRepository.findByIdAndDeleteFlag(slotId, Constants.DELETE_NONE);
        if(!opObservationSlot.isPresent()){
            throw new RecordNotFoundException("Observation slot not found");
        }
        Integer result =opObservationSlot.get().getResult();
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.SUCCESS, result);

    }

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
        observationSlot.setResult(0);
        observationSlot.setObservationPlan(opObservationPlan.get());
        observationSlot.setCreate();
        observationSlotRepository.save(observationSlot);
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.CREATE_SUCCESS, null);
    }
    public ApiResponse getObservationSlotDetail(Integer oSlotId){
        Optional<ObservationSlot> opObservationSlotById = observationSlotRepository.findByIdAndDeleteFlag(oSlotId, Constants.DELETE_NONE);
        ObservationSlotRequest observationSlotRequest=new ObservationSlotRequest();
        observationSlotRequest.setId(opObservationSlotById.get().getId());
        observationSlotRequest.setSlotTime(opObservationSlotById.get().getSlotTime());
        observationSlotRequest.setSubjectId(opObservationSlotById.get().getSubject().getId());
        observationSlotRequest.setSlotId(opObservationSlotById.get().getSlot().getId());
        observationSlotRequest.setRoomId(opObservationSlotById.get().getRoom().getId());
        observationSlotRequest.setAccountId(opObservationSlotById.get().getAccount().getId());
        observationSlotRequest.setHeadTraining(opObservationSlotById.get().getHeadTraining().getId());
        observationSlotRequest.setReason(opObservationSlotById.get().getReason());
        observationSlotRequest.setClassName(opObservationSlotById.get().getClassName());
        observationSlotRequest.setAccountId1(opObservationSlotById.get().getAccount1().getId());
        observationSlotRequest.setAccountId2(opObservationSlotById.get().getAccount2().getId());
        if(opObservationSlotById.isEmpty()){
            throw new RecordNotFoundException("Observation slot is empty");
        }
        return new ApiResponse(Constants.HTTP_CODE_200,Constants.SUCCESS,observationSlotRequest);
    }

    // danh sach slot theo ke hoach
    public ApiResponse listOfObservationSlotByPlanId(Integer planId) {
        List<Object> listOfObservationSlot = observationSlotDao.listOfObservationSlotByPlanId(planId);
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.SUCCESS, listOfObservationSlot);
    }
    // hiên thi ket qua danh gia của GV đi dự giờ theo  slot id
    public ApiResponse resultObservationSlotById(Integer observationSlotId){
//        List<Object> listResultObservationSlot = observationSlotDao.resultObservationSlot(observationSlotId);
//        List<ObservationReview> listofResultObservationReview=listResultObservationSlot.stream().map(obj->
//                                                                (ObservationReview)obj).collect(Collectors.toList());
        List<ObservationReview> listOfResultObservationReview =
                observationReviewRepository.findAllByObservationSlotIdAndDeleteFlag(observationSlotId, Constants.DELETE_NONE);
        List<EvaluationObservationReivewDetail> listOfEvaluationObservationReivewDetail=new ArrayList<>();
        EvaluationObservationReivewDetail evaluationObservationReivewDetail=null;
        List<ObservationDetail> listOfObservationDetail = new ArrayList<>();
        for(ObservationReview observationReview:listOfResultObservationReview){
            evaluationObservationReivewDetail =new EvaluationObservationReivewDetail();
            evaluationObservationReivewDetail.setAdvantage(observationReview.getAdvantage());
            evaluationObservationReivewDetail.setDisadvantage(observationReview.getDisadvantage());
            evaluationObservationReivewDetail.setComment(observationReview.getComment());
            listOfObservationDetail=observationDetailRepository.findByObservationReviewId(
                    observationReview.getId());
            evaluationObservationReivewDetail.setListOfObservationDetail(listOfObservationDetail);
            listOfEvaluationObservationReivewDetail.add(evaluationObservationReivewDetail);
        }


        return new ApiResponse(Constants.HTTP_CODE_200, Constants.SUCCESS, listOfEvaluationObservationReivewDetail);
    }
    // đánh giá slot đó đạt hay ko đạt
    public ApiResponse passResultObservationSlot(Integer observationSlotId,Integer pass){
        Optional<ObservationSlot> opObservationSlot = observationSlotRepository.findByIdAndDeleteFlag(observationSlotId,
                Constants.DELETE_NONE);
        opObservationSlot.get().setResult(pass);
        observationSlotRepository.save(opObservationSlot.get());
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.UPDATE_SUCCESS, null);

    }
    // danh sach cac slot cua CNBM theo kì
    public ApiResponse listObservationSlotBySemester(Integer semesterId,Integer accountId){
        List<Object> listObservationSlot = observationSlotDao.listObservationSlotBySemester(semesterId, accountId);
        //List<ObservationSlot> listObservationSlot =
                observationSlotRepository.findAllByHeadSubjectAndSemester(semesterId,accountId);
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
