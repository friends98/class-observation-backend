package com.observationclass.service;

import com.observationclass.entity.Criteria;
import com.observationclass.model.ApiResponse;
import com.observationclass.model.request.CriteriaRequest;
import com.observationclass.repository.CriteriaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.assertj.core.api.Assertions.assertThat;
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class CriteriaServiceTest {
    @MockBean
    private CriteriaRepository criteriaRepository;
    @InjectMocks
    private CriteriaService criteriaService;

    @Test
    void getListCriteria() {
        Criteria criteria =new Criteria();
        List<Criteria> listOfCriteria =new ArrayList<>();
        listOfCriteria.add(criteria);
        listOfCriteria.add(criteria);
        Mockito.when(criteriaRepository.findAllByDeleteFlag(anyInt())).thenReturn(listOfCriteria);
        ApiResponse apiResponseActual =criteriaService.getListCriteria();
        assertThat(apiResponseActual.getItems()).isNotNull();

    }

    @Test
    void addNewCriteria() {
        Criteria criteria = new Criteria();
        CriteriaRequest criteriaRequest=new CriteriaRequest();
        criteriaRequest.setCriteriaName("phong cach giang day");
        criteria.setId(1);
        criteria.setCriteriaName(criteriaRequest.getCriteriaName());

        Mockito.when(criteriaRepository.save(Mockito.any(Criteria.class))).thenReturn(criteria);
        ApiResponse apiResponseActual = criteriaService.addNewCriteria(criteriaRequest);
        assertThat(apiResponseActual).isNotNull();
    }

    @Test
    void updateCriteria() {
        Criteria criteria = new Criteria();
        CriteriaRequest criteriaRequest=new CriteriaRequest();
        criteriaRequest.setId(1);
        criteriaRequest.setCriteriaName("phong cach giang day");
        criteria.setId(1);
        criteria.setCriteriaName(criteriaRequest.getCriteriaName());

        Mockito.when(criteriaRepository.save(Mockito.any(Criteria.class))).thenReturn(criteria);
        ApiResponse apiResponseActual = criteriaService.updateCriteria(criteriaRequest);
        assertThat(apiResponseActual).isNotNull();
    }

    @Test
    void deleteCriteriaById() {
    }

    @Test
    void setNewCritera() {
    }
}