package com.observationclass.controller;

import com.observationclass.model.ApiResponse;
import com.observationclass.model.request.ObservationReviewRequest;
import com.observationclass.service.ObservationReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/lecture")
public class ObservationReviewController {
    @Autowired
    private ObservationReviewService observationReviewService;

    @GetMapping("list-observation-review")
    public ResponseEntity<ApiResponse> listObservationReview(@RequestParam(name = "campusId") Integer campusId,
                                                             @RequestParam(name = "semesterId") Integer semesterId,
                                                             @RequestParam(name = "accountId") Integer accountId) {
        return ResponseEntity.ok().body(observationReviewService.listObservationReviewBySemester(campusId, semesterId, accountId));
    }

    @PostMapping("/create-observation-review")
    public ResponseEntity<ApiResponse> createObservationReview(@RequestBody @Valid ObservationReviewRequest observationReviewRequest) {
        return ResponseEntity.ok().body(observationReviewService.createObservationReview(observationReviewRequest));
    }
}
