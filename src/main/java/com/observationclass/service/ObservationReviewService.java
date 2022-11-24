package com.observationclass.service;

import com.observationclass.common.Constants;
import com.observationclass.entity.Account;
import com.observationclass.entity.ObservationDetail;
import com.observationclass.entity.ObservationReview;
import com.observationclass.entity.ObservationSlot;
import com.observationclass.model.ApiResponse;
import com.observationclass.model.request.ObservationDetailRequest;
import com.observationclass.model.request.ObservationReviewRequest;
import com.observationclass.model.response.EvaluationObservationReivewDetail;
import com.observationclass.repository.AccountRepository;
import com.observationclass.repository.ObservationDetailRepository;
import com.observationclass.repository.ObservationReviewRepository;
import com.observationclass.repository.ObservationSlotRepository;
import com.observationclass.repository.dao.ObservationReviewDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ObservationReviewService {
    @Autowired
    private ObservationDetailRepository observationDetailRepository;

    @Autowired
    private ObservationReviewRepository observationReviewRepository;

    @Autowired
    private ObservationSlotRepository observationSlotRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ObservationReviewDao observationReviewDao;



    public ApiResponse listObservationReviewBySemester( Integer campusId, Integer semesterId,Integer accountId) {
        List<Object> listObservationReview = new ArrayList<>();
        if(accountId !=null && campusId!=null && semesterId!=null ){
            listObservationReview.addAll(observationReviewDao.listObservationReview(campusId,semesterId,accountId));
        }
        return new ApiResponse(Constants.HTTP_CODE_200,Constants.SUCCESS,listObservationReview);
    }
    public ApiResponse listResultObservationReviewBySemester( Integer campusId, Integer semesterId,Integer accountId) {
        List<Object> listObservationReview = new ArrayList<>();
        if(accountId !=null && campusId!=null && semesterId!=null ){
            listObservationReview.addAll(observationReviewDao.listObservationReview(campusId,semesterId,accountId));
        }
        return new ApiResponse(Constants.HTTP_CODE_200,Constants.SUCCESS,listObservationReview);
    }



    public ApiResponse viewMyEvaluationDetail(Integer slotId, Integer accountId) {
        Optional<ObservationReview> opObservationReview = observationReviewRepository.findByAccountAndObservationSlot(slotId,
                accountId);
        List<ObservationDetail> listOfObservationDetail = observationDetailRepository.findByObservationReviewId(
                opObservationReview.get().getId());
        EvaluationObservationReivewDetail evaluationObservationReivewDetail=new EvaluationObservationReivewDetail();
        evaluationObservationReivewDetail.setAdvantage(opObservationReview.get().getAdvantage());
        evaluationObservationReivewDetail.setDisadvantage(opObservationReview.get().getDisadvantage());
        evaluationObservationReivewDetail.setComment(opObservationReview.get().getComment());
        evaluationObservationReivewDetail.setStatus(opObservationReview.get().getStatus());
        evaluationObservationReivewDetail.setListOfObservationDetail(listOfObservationDetail);
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.SUCCESS, evaluationObservationReivewDetail);
    }

    public ApiResponse createObservationReview(ObservationReviewRequest observationReviewRequest) {
        ObservationReview observationReview = new ObservationReview();
        Optional<ObservationSlot> opObservationSlot = observationSlotRepository.findById(observationReviewRequest.
                getObservationSlotId());
        Optional<Account> opAccount = accountRepository.findById(observationReviewRequest.getAccountId());
        if (opObservationSlot.isPresent() && opAccount.isPresent()) {
            String lessonName = observationReviewRequest.getLessonName();
            String advantage = observationReviewRequest.getAdvantage();
            String disadvantage = observationReviewRequest.getDisadvantage();
            String comment =observationReviewRequest.getComment();
            Integer totalPoint = getTotalPointFromRequest(observationReviewRequest);
            //Integer accountId =observationReviewRequest.getAccountId();
            //Integer observationSlotId =observationReviewRequest.getObservationSlotId();
            observationReview.setLessonName(lessonName);
            observationReview.setAdvantage(advantage);
            observationReview.setDisadvantage(disadvantage);
            observationReview.setComment(comment);
            observationReview.setTotalPoint(totalPoint);
            observationReview.setObservationSlot(opObservationSlot.get());
            observationReview.setAccount(opAccount.get());
            observationReview.setStatus(1);
            observationReview.setCreate();
            observationReviewRepository.save(observationReview);
            List<ObservationDetail> lstObservationDetail = new ArrayList<>();
            observationReviewRequest.getObservationDetailRequests().forEach(d->{
                ObservationDetail observationDetail =new ObservationDetail();
                observationDetail.setCode(d.getCode());
                observationDetail.setName(d.getName());
                observationDetail.setPoint(d.getPoint());
                observationDetail.setObservationReview(observationReview);
                lstObservationDetail.add(observationDetail);
            });
            Collections.reverse(lstObservationDetail);
            observationDetailRepository.saveAll(lstObservationDetail);
        }
        return new ApiResponse(Constants.HTTP_CODE_200,Constants.CREATE_SUCCESS,null);
    }

    public static Integer getTotalPointFromRequest(ObservationReviewRequest observationReviewRequest) {
        Set<ObservationDetailRequest> setObservationDetail = observationReviewRequest.getObservationDetailRequests();
        Integer totalPoint = setObservationDetail.stream().mapToInt(ObservationDetailRequest::getPoint).sum();
        return totalPoint;
    }
}

