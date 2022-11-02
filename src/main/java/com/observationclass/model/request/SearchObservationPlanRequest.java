package com.observationclass.model.request;

import lombok.Data;

@Data
public class SearchObservationPlanRequest {
    private Integer semesterId;
    private Integer departmentId;
}
