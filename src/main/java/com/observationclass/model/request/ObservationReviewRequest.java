package com.observationclass.model.request;

import com.observationclass.entity.ObservationDetail;
import lombok.Data;

import java.util.Set;

@Data
public class ObservationReviewRequest {
    private Integer id;
    private String lessonName;
    private String advantage;
    private String disadvantage;
    private String comment;
    private Integer totalPoint;
    private Integer accountId;
    private Integer observationSlotId;
    private Set<ObservationDetailRequest> observationDetailRequests;
}
