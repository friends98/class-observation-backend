package com.observationclass.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultObservationSlotResponse {
    private String lectureName;
    private String advantage;
    private String disadvantage;
    private String comment;
    private Integer totalPoint;
}
