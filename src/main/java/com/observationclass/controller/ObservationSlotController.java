package com.observationclass.controller;

import com.observationclass.model.ApiResponse;
import com.observationclass.model.request.ObservationSlotRequest;
import com.observationclass.service.ObservationSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class ObservationSlotController {
    @Autowired
    private ObservationSlotService observationSlotService;

    @GetMapping("/list-observation-slot")
    public ResponseEntity<ApiResponse> listObservationSlotBySemester(@RequestParam(name = "semesterId") Integer semesterId,
                                                                     @RequestParam(name = "accountId") Integer accountId) {
        return ResponseEntity.ok().body(observationSlotService.listObservationSlotBySemester(semesterId, accountId));
    }

    @PostMapping("/update-observation-slot")
    public ResponseEntity<ApiResponse> updateObservationSlotById(@RequestBody @Valid ObservationSlotRequest observationSlotRequest) {
        return ResponseEntity.ok().body(observationSlotService.updateObservationSlot(observationSlotRequest));
    }

    @GetMapping("/result-observation-slot")
    public ResponseEntity<ApiResponse> resultObservationSlot(@RequestParam(name = "oSlotId") Integer observationSlotId) {
        return ResponseEntity.ok().body(observationSlotService.resultObservationSlotById(observationSlotId));
    }

    @PostMapping("/reject-observation-slot")
    public ResponseEntity<ApiResponse> rejectObservationSlot(@RequestParam(name = "oSlotId") Integer observationSlotId) {
        return ResponseEntity.ok().body(observationSlotService.rejectResultObservationSlot(observationSlotId));
    }


}
