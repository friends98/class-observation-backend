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
    //24 lấy observation slot detail
    @GetMapping("/detail-observation-slot")
    public ResponseEntity<ApiResponse> getDetailObservationSlotById(@RequestParam(name = "oSlotId") Integer oSlotId) {
        return ResponseEntity.ok().body(observationSlotService.getObservationSlotDetail(oSlotId));
    }

    @GetMapping("/list-observation-slot")
    public ResponseEntity<ApiResponse> listObservationSlotBySemester(@RequestParam(name = "semesterId") Integer semesterId,
                                                                     @RequestParam(name = "accountId") Integer accountId) {
        return ResponseEntity.ok().body(observationSlotService.listObservationSlotBySemester(semesterId, accountId));
    }
    // danh sach slot nằm trong kế hoạch theo plan id cho dao tao xem
    @GetMapping("/list-observation-slot-plan")
    public ResponseEntity<ApiResponse> listObservationSlotByPlan(@RequestParam(name = "planId") Integer planId) {
        return ResponseEntity.ok().body(observationSlotService.listOfObservationSlotByPlanId(planId));
    }

    @PostMapping("/update-observation-slot")
    public ResponseEntity<ApiResponse> updateObservationSlotById(@RequestBody @Valid ObservationSlotRequest observationSlotRequest) {
        return ResponseEntity.ok().body(observationSlotService.updateObservationSlot(observationSlotRequest));
    }
    // ket qua cua tung slot cho cả trưởng bộ môn và giảng viên
    @GetMapping("/result-observation-slot")
    public ResponseEntity<ApiResponse> resultObservationSlot(@RequestParam(name = "oSlotId") Integer observationSlotId) {
        return ResponseEntity.ok().body(observationSlotService.resultObservationSlotById(observationSlotId));
    }

    // phe duyet ket qua slot
    @PostMapping("/reject-observation-slot")
    public ResponseEntity<ApiResponse> rejectObservationSlot(@RequestParam(name = "oSlotId") Integer observationSlotId) {
        return ResponseEntity.ok().body(observationSlotService.rejectResultObservationSlot(observationSlotId));
    }
    // tao moi slot theo plan id
    @PostMapping("/add-new-slot")
    public ResponseEntity<ApiResponse> addNewSlot(@RequestBody ObservationSlotRequest observationSlotRequest
                                                                    , @RequestParam(name = "planId") Integer planId) {
        return ResponseEntity.ok().body(observationSlotService.createNewSlot(observationSlotRequest, planId));
    }
}
