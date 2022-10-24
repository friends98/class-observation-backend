package com.observationclass.model.request;

import lombok.Data;

@Data
public class ObservationPlanRequest {
    private Integer id;

    private Integer semesterId;
    private Integer campusId;
    private Integer departmentId;
    private Integer accountId;
    private Integer planStatus;

}
