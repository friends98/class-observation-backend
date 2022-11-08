package com.observationclass.controller;

import com.observationclass.model.ApiResponse;
import com.observationclass.model.request.ObservationReviewRequest;
import com.observationclass.service.ObservationReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/lecture")
public class ObservationReviewController {
    @Autowired
    private ObservationReviewService observationReviewService;

    @PostMapping("/create-observation-review")
    public ResponseEntity<ApiResponse> createObservationReview(@RequestBody @Valid ObservationReviewRequest observationReviewRequest) {
        return ResponseEntity.ok().body(observationReviewService.createObservationReview(observationReviewRequest));
    }
}
