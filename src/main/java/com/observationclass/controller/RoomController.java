package com.observationclass.controller;

import com.observationclass.model.response.DropdownListResponse;
import com.observationclass.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping("/room-dropdown-list")
    public List<DropdownListResponse> roomDropdownList(@RequestParam(name = "id") Integer campusId
            , @RequestParam(name = "name") String name) {
        return roomService.getRoomDropdownList(campusId, name);
    }
}
