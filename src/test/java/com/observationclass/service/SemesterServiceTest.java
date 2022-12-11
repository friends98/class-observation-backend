package com.observationclass.service;

import com.observationclass.model.response.DropdownListResponse;
import com.observationclass.repository.SemesterRepository;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class SemesterServiceTest {
    @MockBean private SemesterRepository semesterRepository;
    @InjectMocks private SemesterService semesterService;
    @Test
    void getSemesterDropdownList() {

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
        List<DropdownListResponse> listOfSemester =new ArrayList<>();
        listOfSemester.add(new obj());
        Mockito.when(semesterRepository.semesterDropdownList()).thenReturn(listOfSemester);
        List<DropdownListResponse> listOfSemesterActual =semesterService.getSemesterDropdownList();
        assertEquals(1, listOfSemesterActual.size());
    }
}