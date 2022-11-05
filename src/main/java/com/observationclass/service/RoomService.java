package com.observationclass.service;

import com.observationclass.common.Constants;
import com.observationclass.exception.RecordNotFoundException;
import com.observationclass.model.ApiResponse;
import com.observationclass.model.response.DropdownListResponse;
import com.observationclass.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    public ApiResponse getDropdownlistRoomByCampusId(Integer campusId) {
        List<DropdownListResponse> roomDropdownList = roomRepository.findAllByCampusId(campusId);
        if (roomDropdownList == null) {
            throw new RecordNotFoundException(Constants.RECORD_DOES_NOT_EXIST);
        }
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.SUCCESS, roomDropdownList);
    }
}
