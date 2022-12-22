package com.observationclass.service;

import com.observationclass.entity.ObservationPlan;
import com.observationclass.model.ApiResponse;
import com.observationclass.repository.ObservationPlanRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.Random;

import static org.mockito.ArgumentMatchers.anyInt;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)

class ObservationPlanServiceTest {
    @MockBean
    private ObservationPlanRepository observationPlanRepository;

    @InjectMocks
    private ObservationPlanService observationPlanService;

    @Mock
    private ObservationSlotService observationSlotService;

    @Test
    void testStatusPlanById_WhenPlanIdValid() throws Exception{
        ObservationPlan observationPlan = new ObservationPlan();
        observationPlan.setId(1);
        observationPlan.setPlanStatus(1);
        Mockito.when(observationPlanRepository.findByIdAndDeleteFlag(anyInt(), anyInt()))
                                                .thenReturn(Optional.of(observationPlan));

        ApiResponse apiResponseActual =observationPlanService.getStatusPlanById(5);
        System.out.println(apiResponseActual.toString());
        Integer planStatus=(Integer)apiResponseActual.getItems();
        System.out.println(apiResponseActual.getItems());
        assertEquals(0,planStatus);

    }

    @Test
    void approveObservationPlan() {
    }

    @Test
    void listPlanBySemesterAndCampus() {
    }

    @Test
    void createNewObservationPlan() {
    }

    @Test
    void updateOnlyObservationPlan() {
    }

    @Test
    void updateOnlyObservationSlot() {
    }

    @Test
    void deleteObservationPlan() {
    }

    @Test
    void getAccountById() {
    }

    @Test
    void getSubjectById() {
    }

    @Test
    void getSlotById() {
    }

    @Test
    void getRoomById() {
    }
    public Integer getRandom(){
        return new Random().ints(1,10).findFirst().getAsInt();
    }
}