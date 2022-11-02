package com.observationclass.controller;


import com.observationclass.entity.ObservationPlan;
import com.observationclass.model.ApiResponse;
import com.observationclass.model.request.ObservationPlanRequest;
import com.observationclass.model.request.ObservationPlanUpdateRequest;
import com.observationclass.service.ObservationPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/admin")
public class ObservationPlanController {

    @Autowired
    private ObservationPlanService observationPlanService;


    @PostMapping("/createObservationPlan")
    public ResponseEntity<ApiResponse> createObservationPlan(@RequestBody @Valid ObservationPlanRequest observationPlanRequest){
        return ResponseEntity.ok().body(observationPlanService.createObservationPlan(observationPlanRequest));
    }
    @PostMapping("/updateObservationPlan")
    public ResponseEntity<ApiResponse> updateObservationPlan(@RequestBody @Valid ObservationPlanUpdateRequest
                                                                         observationPlanUpdateRequest){
        return ResponseEntity.ok().body(observationPlanService.updateObservationPlan(observationPlanUpdateRequest));
    }
    @PostMapping("/deleteObservation")
    public ResponseEntity<ApiResponse> deleteObservation(@RequestParam(name = "id")Integer id){
        return ResponseEntity.ok().body(observationPlanService.deleteObservationPlan(id));
    }


//    @PostMapping("/updateObservationPlan")
//    public ResponseEntity<ApiResponse> updateObservationPlan(@RequestBody @Valid ObservationPlanRequest observationPlanRequest) {
//        return ResponseEntity.ok().body(observationPlanService.updateObservationPlan(observationPlanRequest));
//    }

}
