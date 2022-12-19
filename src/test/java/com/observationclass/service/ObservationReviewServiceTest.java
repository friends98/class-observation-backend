package com.observationclass.service;

import com.observationclass.entity.Account;
import com.observationclass.entity.ObservationDetail;
import com.observationclass.entity.ObservationReview;
import com.observationclass.entity.ObservationSlot;
import com.observationclass.model.ApiResponse;
import com.observationclass.model.request.ObservationReviewRequest;
import com.observationclass.repository.AccountRepository;
import com.observationclass.repository.ObservationDetailRepository;
import com.observationclass.repository.ObservationReviewRepository;
import com.observationclass.repository.ObservationSlotRepository;
import com.observationclass.repository.dao.ObservationReviewDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.assertj.core.api.Assertions.assertThat;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anySet;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class ObservationReviewServiceTest {
    @MockBean
    private ObservationDetailRepository observationDetailRepository;

    @MockBean
    private ObservationReviewRepository observationReviewRepository;

    @MockBean
    private ObservationSlotRepository observationSlotRepository;

    @MockBean
    private AccountRepository accountRepository;

    @Mock
    private ObservationReviewDao observationReviewDao;

    @InjectMocks
    private ObservationReviewService observationReviewService;


    @Test
    void listObservationReviewBySemester() {
        List<Object> listOfObject = new ArrayList<>();
        Mockito.when(observationReviewDao.listObservationReview(anyInt(), anyInt(), anyInt())).thenReturn(listOfObject);
        ApiResponse apiResponseActual =observationReviewService.listObservationReviewBySemester(1,1,1);
        assertThat(apiResponseActual.getItems()).isNotNull();

    }

    @Test
    void listResultObservationReviewBySemester() {
        List<Object> listOfObject=new ArrayList<>();
        Mockito.when(observationReviewDao.listObservationReview(anyInt(),anyInt(),anyInt())).thenReturn(listOfObject);
        ApiResponse apiResponseActual = observationReviewService.listResultObservationReviewBySemester(1, 1, 1);
        assertThat(apiResponseActual.getItems()).isNotNull();
    }

    @Test
    void viewMyEvaluationDetail() {


    }

    @Test
    void createObservationReview() {
        ObservationReview observationReview = new ObservationReview();
        ObservationSlot observationSlot = new ObservationSlot();
        Account account =new Account();
        List<ObservationDetail> lstObservationDetail = new ArrayList<>();
        ObservationReviewRequest observationReviewRequest = new ObservationReviewRequest();
        observationReviewRequest.setId(1);
        Mockito.when(observationSlotRepository.findById(anyInt())).thenReturn(Optional.ofNullable(observationSlot));
        Mockito.when(observationReviewRepository.save(Mockito.any(ObservationReview.class))).thenReturn(observationReview);
        Mockito.when(observationDetailRepository.saveAll(anySet())).thenReturn(lstObservationDetail);
        ApiResponse apiResponseActual = observationReviewService.createObservationReview(observationReviewRequest);
        assertThat(apiResponseActual).isNotNull();

    }

    @Test
    void getTotalPointFromRequest() {
    }
}