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
    // hien thị đánh giá chi tiết
    @GetMapping("/view-evaluation-observation-review")
    public ResponseEntity<ApiResponse> evaluationObservationReivewDetail(@RequestParam(name = "slotId") Integer slotId,
                                                                         @RequestParam(name = "accountId") Integer accountId) {
        return ResponseEntity.ok().body(observationReviewService.viewMyEvaluationDetail(slotId, accountId));
    }
    //danh sach các reiview trong kì mà giảng viên phải đi dự giờ
    @GetMapping("/list-observation-review")
    public ResponseEntity<ApiResponse> listObservationReview(@RequestParam(name = "campusId") Integer campusId,
                                                             @RequestParam(name = "semesterId") Integer semesterId,
                                                             @RequestParam(name = "accountId") Integer accountId) {
        return ResponseEntity.ok().body(observationReviewService.listObservationReviewBySemester(campusId, semesterId, accountId));
    }
    // danh sach cac kết quả của giảng viên đó trong kì học
    @GetMapping("/list-result-observation-review")
    public ResponseEntity<ApiResponse> listResultObservationReview(@RequestParam(name = "campusId") Integer campusId,
                                                             @RequestParam(name = "semesterId") Integer semesterId,
                                                             @RequestParam(name = "accountId") Integer accountId) {
        return ResponseEntity.ok().body(observationReviewService.listResultObservationReviewBySemester(campusId, semesterId, accountId));
    }
    //nhập phiếu đánh giá
    @PostMapping("/create-observation-review")
    public ResponseEntity<ApiResponse> createObservationReview(@RequestBody @Valid ObservationReviewRequest observationReviewRequest) {
        return ResponseEntity.ok().body(observationReviewService.createObservationReview(observationReviewRequest));
    }
}
