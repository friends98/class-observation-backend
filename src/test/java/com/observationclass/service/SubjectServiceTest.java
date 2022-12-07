package com.observationclass.service;

import com.observationclass.model.response.DropdownListResponse;
import com.observationclass.repository.SubjectRepository;
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
import static org.mockito.ArgumentMatchers.anyString;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class SubjectServiceTest {
    @MockBean
    private SubjectRepository subjectRepository;

    @InjectMocks
    private SubjectService subjectService;

    @Test
    void getSubjectDropdownList() {
        class obj implements DropdownListResponse {

            @Override
            public Integer getValue() {
                return null;
            }

            @Override
            public Integer setValue(Integer value) {
                return null;
            }

            @Override
            public String getName() {
                return null;
            }

            @Override
            public String setName() {
                return null;
            }
        }
        List<DropdownListResponse> listOfSubject = new ArrayList<>();
        listOfSubject.add(new obj());

        Mockito.when(subjectRepository.findAllAndCampusId(anyInt(), anyString())).thenReturn(listOfSubject);
        List<DropdownListResponse>listOfSubjectActual =subjectService.getSubjectDropdownList(1,"Pro");
        assertEquals(1,listOfSubjectActual.size());
    }
}