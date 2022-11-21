package com.observationclass.controller;

import com.observationclass.model.response.DropdownListResponse;
import com.observationclass.repository.SlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SlotController {
    @Autowired
    private SlotRepository slotRepository;
    @GetMapping("/slot-list")
    List<DropdownListResponse> slotDropdownList(){
        return slotRepository.slotDropdownList();
    }
}
