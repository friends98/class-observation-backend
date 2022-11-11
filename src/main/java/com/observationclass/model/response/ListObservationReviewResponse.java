package com.observationclass.model.response;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class ListObservationReviewResponse {
    private Integer id;
    private String lectureName;
    private Integer slotName;
    private Timestamp slotTime;
    private String className;
    private String roomName;
    private String subjectName;
    private String departmentName;
}
