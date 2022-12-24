package com.observationclass.model.request;

import lombok.Data;

@Data
public class ObservationPlanUpdateRequest {
    private Integer id;
    private Integer campusId;
    private Integer semesterId;
    private Integer departmentId;
}
