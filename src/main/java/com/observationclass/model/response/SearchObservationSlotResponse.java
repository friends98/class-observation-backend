package com.observationclass.model.response;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class SearchObservationSlotResponse {
    private Integer id;
    private Integer planId;
    private String userName;
    private Timestamp slotTime;
    private String slot;
    private String roomName;
    private String subjectCode;
    private String subjectName;
    private String className;
    private String reason;
    private String headTraining;
    private String lecture1;
    private String lecture2;

}
