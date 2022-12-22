package com.observationclass.service;

import com.observationclass.entity.*;
import com.observationclass.model.ApiResponse;
import com.observationclass.model.request.ObservationSlotRequest;
import com.observationclass.model.response.EvaluationObservationReivewDetail;
import com.observationclass.repository.*;
import com.observationclass.repository.dao.ObservationSlotDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class ObservationSlotServiceTest {
    @MockBean private ObservationSlotRepository observationSlotRepository;

    @MockBean private ObservationPlanRepository observationPlanRepository;
    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private SubjectRepository subjectRepository;

    @MockBean
    private RoomRepository roomRepository;

    @MockBean
    private SlotRepository slotRepository;
    @MockBean
    private ObservationReviewRepository observationReviewRepository;

    @MockBean
    private ObservationDetailRepository observationDetailRepository;



    @InjectMocks private ObservationSlotService observationSlotService;

    @Mock
    private ObservationSlotDao observationSlotDao;

    //pass
    @Test
    void createNewSlot() {
        ObservationPlan observationPlan = new ObservationPlan();
        observationPlan.setId(1);
        ObservationSlot observationSlot = new ObservationSlot();
        ObservationSlotRequest observationSlotRequest=new ObservationSlotRequest();
        observationSlotRequest.setAccountId(1);
        observationSlotRequest.setAccountId2(1);
        observationSlotRequest.setAccountId1(1);
        observationSlotRequest.setRoomId(1);
        observationSlotRequest.setSlotId(1);
        observationSlotRequest.setSubjectId(1);
        observationSlotRequest.setHeadTraining(1);
        observationSlotRequest.setHeadSubject(1);
        Account account = new Account();
        Room room = new Room();
        Subject subject = new Subject();
        Slot slot = new Slot();
        Mockito.when(roomRepository.findById(anyInt())).thenReturn(Optional.ofNullable(room));
        Mockito.when(slotRepository.findById(anyInt())).thenReturn(Optional.ofNullable(slot));
        Mockito.when(accountRepository.findById(anyInt())).thenReturn(Optional.ofNullable(account));
        Mockito.when(subjectRepository.findById(anyInt())).thenReturn(Optional.ofNullable(subject));
        Mockito.when(observationPlanRepository.findById(anyInt())).thenReturn(Optional.ofNullable(observationPlan));
        Mockito.when(observationSlotRepository.save(Mockito.any(ObservationSlot.class))).thenReturn(observationSlot);
        ApiResponse apiResponseActual = observationSlotService.createNewSlot(observationSlotRequest, observationPlan.getId());
        assertThat(apiResponseActual).isNotNull();
    }

    //pass
    @Test
    void getObservationSlotDetail() {
        ObservationSlot observationSlot =new ObservationSlot();
        observationSlot.setId(1);
        Account account = new Account();
        account.setId(1);
        Room room = new Room();
        room.setId(1);
        Subject subject = new Subject();
        subject.setId(1);
        Slot slot = new Slot();
        slot.setId(1);
        observationSlot.setSubject(subject);
        observationSlot.setSlot(slot);
        observationSlot.setHeadSubject(account);
        observationSlot.setHeadTraining(account);
        observationSlot.setAccount(account);
        observationSlot.setAccount1(account);
        observationSlot.setAccount2(account);
        observationSlot.setRoom(room);
        Mockito.when(roomRepository.findById(anyInt())).thenReturn(Optional.ofNullable(room));
        Mockito.when(slotRepository.findById(anyInt())).thenReturn(Optional.ofNullable(slot));
        Mockito.when(accountRepository.findById(anyInt())).thenReturn(Optional.ofNullable(account));
        Mockito.when(subjectRepository.findById(anyInt())).thenReturn(Optional.ofNullable(subject));
        Mockito.when(observationSlotRepository.findByIdAndDeleteFlag(anyInt(), anyInt())).thenReturn(Optional.ofNullable(observationSlot));
        ApiResponse apiResponseActual =observationSlotService.getObservationSlotDetail(1);
        assertThat(apiResponseActual.getItems()).isNotNull();
    }

    //pass
    @Test
    void listOfObservationSlotByPlanId() {
        List<Object> listOfObject = new ArrayList<>();
        listOfObject.add(new Object());
        listOfObject.add(new Object());
        Mockito.when(observationSlotDao.listOfObservationSlotByPlanId(1)).thenReturn(listOfObject);
        List<Object> listOfObservationSlotActual = (ArrayList)observationSlotDao.listOfObservationSlotByPlanId(1);
        assertEquals(listOfObject.size(),listOfObservationSlotActual.size());
    }

    //pass
    @Test
    void resultObservationSlotById() {
        List<ObservationReview> listOfResultObservationReview = new ArrayList<>();
        listOfResultObservationReview.add(new ObservationReview());
        listOfResultObservationReview.add(new ObservationReview());
        List<EvaluationObservationReivewDetail> listOfEvaluationObservationReivewDetail=new ArrayList<>();
        listOfEvaluationObservationReivewDetail.add(new EvaluationObservationReivewDetail());
        listOfEvaluationObservationReivewDetail.add(new EvaluationObservationReivewDetail());
        List<ObservationDetail> listOfObservationDetail = new ArrayList<>();
        listOfObservationDetail.add(new ObservationDetail());
        listOfObservationDetail.add(new ObservationDetail());
        Mockito.when(observationReviewRepository.findAllByObservationSlotIdAndDeleteFlag(anyInt(), anyInt())).thenReturn(listOfResultObservationReview);
        Mockito.when(observationDetailRepository.findByObservationReviewId(anyInt())).thenReturn(listOfObservationDetail);
        ApiResponse apiResponseActual=observationSlotService.resultObservationSlotById(1);
        assertThat(apiResponseActual.getItems()).isNotNull();

    }
    // pass
    @Test
    void passResultObservationSlot() {
        ObservationSlot observationSlot = new ObservationSlot();
        ObservationSlot observationSlotResponse = new ObservationSlot();
        observationSlotResponse.setId(1);
        observationSlotResponse.setResult(1);
        Mockito.when(observationSlotRepository.findByIdAndDeleteFlag(anyInt(), anyInt())).thenReturn(Optional.ofNullable(observationSlot));
        Mockito.when(observationSlotRepository.save(Mockito.any(ObservationSlot.class))).thenReturn(observationSlotResponse);
        ApiResponse apiResponseActual =observationSlotService.passResultObservationSlot(1,1);
        assertThat(apiResponseActual).isNotNull();
    }
    // pass
    @Test
    void listObservationSlotBySemester() {
        List<Object> listOfObject = new ArrayList<>();
        Mockito.when(observationSlotDao.listObservationSlotBySemester(anyInt(),anyInt())).thenReturn(listOfObject);
        ApiResponse apiResponseActual =observationSlotService.listObservationSlotBySemester(1,1);
        assertThat(apiResponseActual).isNotNull();
    }

    @Test
    void updateObservationSlot() {
        ObservationSlot observationSlot = new ObservationSlot();
        ObservationSlotRequest observationSlotRequest=new ObservationSlotRequest();
        observationSlotRequest.setId(1);
        observationSlotRequest.setAccountId(1);
        observationSlotRequest.setAccountId2(1);
        observationSlotRequest.setAccountId1(1);
        observationSlotRequest.setRoomId(1);
        observationSlotRequest.setSlotId(1);
        observationSlotRequest.setSubjectId(1);
        observationSlotRequest.setHeadTraining(1);
        observationSlotRequest.setHeadSubject(1);
        Account account = new Account();
        Room room = new Room();
        Subject subject = new Subject();
        Slot slot = new Slot();
        Mockito.when(roomRepository.findById(anyInt())).thenReturn(Optional.ofNullable(room));
        Mockito.when(slotRepository.findById(anyInt())).thenReturn(Optional.ofNullable(slot));
        Mockito.when(accountRepository.findById(anyInt())).thenReturn(Optional.ofNullable(account));
        Mockito.when(subjectRepository.findById(anyInt())).thenReturn(Optional.ofNullable(subject));
        Mockito.when(observationSlotRepository.findByIdAndDeleteFlag(anyInt(),anyInt())).thenReturn(Optional.ofNullable(observationSlot));
        ApiResponse apiReponseActual =observationSlotService.updateObservationSlot(observationSlotRequest);
        assertThat(apiReponseActual).isNotNull();
    }
}
