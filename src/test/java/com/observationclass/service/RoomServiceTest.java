package com.observationclass.service;

import com.observationclass.model.response.DropdownListResponse;
import com.observationclass.repository.RoomRepository;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)

class RoomServiceTest {
    @MockBean private RoomRepository roomRepository;

    @InjectMocks
    private RoomService roomService;


    @Test
    void getRoomDropdownList() {
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
        List<DropdownListResponse> listOfRoom = new ArrayList<>();
        listOfRoom.add(new obj());
        Mockito.when(roomRepository.findByCampusId(anyInt(), anyString())).thenReturn(listOfRoom);
        List<DropdownListResponse> listOfRoomActual =roomService.getRoomDropdownList(1,"A");
        assertEquals(1,listOfRoomActual.size());

    }
}