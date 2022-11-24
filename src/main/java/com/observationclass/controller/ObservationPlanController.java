package com.observationclass.controller;


import com.observationclass.entity.ObservationPlan;
import com.observationclass.model.ApiResponse;
import com.observationclass.model.request.ObservationPlanRequest;
import com.observationclass.model.request.ObservationPlanUpdateRequest;
import com.observationclass.model.request.ObservationSlotRequest;
import com.observationclass.service.ObservationPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class ObservationPlanController {

    @Autowired
    private ObservationPlanService observationPlanService;
    // lấy trạng thái 24
    @GetMapping("/status-observation-plan")
    public ResponseEntity<ApiResponse> statusObservationPlan(@RequestParam(name="planId")Integer planId){
        return ResponseEntity.ok().body(observationPlanService.getStatusPlanById(planId));
    }
    //24
    @PostMapping("/approve-observation-plan")
    public ResponseEntity<ApiResponse> approveObservationPlan(@RequestParam(name = "planId") Integer planId
            , @RequestParam(name = "statusId") Integer statusId) {
        return ResponseEntity.ok().body(observationPlanService.approveObservationPlan(planId, statusId));
    }

    @GetMapping("/list-search-observation-plan")
    public ResponseEntity<ApiResponse> listSearchObservationPlan(@RequestParam(name="campusId")Integer campusId,
                                                                 @RequestParam(name="semesterId") Integer semesterId) {
        return ResponseEntity.ok().body(observationPlanService.listPlanBySemesterAndCampus(campusId,semesterId));
    }

    @PostMapping("/create-observation-plan")
    public ResponseEntity<ApiResponse> createObservationPlan(@RequestBody @Valid ObservationPlanRequest observationPlanRequest) {
        return ResponseEntity.ok().body(observationPlanService.createNewObservationPlan(observationPlanRequest));
    }

    @PostMapping("/update-only-plan")
    public ResponseEntity<ApiResponse> updateOnlyObservationPlan(@RequestBody @Valid ObservationPlanUpdateRequest
                                                                     observationPlanUpdateRequest) {
        return ResponseEntity.ok().body(observationPlanService.updateOnlyObservationPlan(observationPlanUpdateRequest));
    }
    @PostMapping("/update-only-slot")
    public ResponseEntity<ApiResponse> updateOnlyObservationSlot(@RequestBody @Valid ObservationSlotRequest
                                                                     observationSlotRequest) {
        return ResponseEntity.ok().body(observationPlanService.updateOnlyObservationSlot(observationSlotRequest));
    }

    @PostMapping("/delete-plan")
    public ResponseEntity<ApiResponse> deleteObservation(@RequestParam(name = "id") Integer id) {
        return ResponseEntity.ok().body(observationPlanService.deleteObservationPlan(id));
    }


//    @PostMapping("/updateObservationPlan")
//    public ResponseEntity<ApiResponse> updateObservationPlan(@RequestBody @Valid ObservationPlanRequest observationPlanRequest) {
//        return ResponseEntity.ok().body(observationPlanService.updateObservationPlan(observationPlanRequest));
//    }

}
