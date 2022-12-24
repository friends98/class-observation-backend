package com.observationclass.model.response;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class SearchObservationPlanResponse {
    private Integer id;
    private String departmentName;
    private Integer planStatus;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
