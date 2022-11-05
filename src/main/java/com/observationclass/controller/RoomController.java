package com.observationclass.controller;

import com.observationclass.model.ApiResponse;
import com.observationclass.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RoomController {
    @Autowired
    private RoomService roomService;
    @GetMapping("/room-dropdown-list")
    public ResponseEntity<ApiResponse> roomDropdownListByCampusId(@RequestParam(name = "id") Integer campusId) {
        return ResponseEntity.ok().body(roomService.getDropdownlistRoomByCampusId(campusId));
    }
}
