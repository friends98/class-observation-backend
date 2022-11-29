package com.observationclass.model.response;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
public class SearchObservationSlotByPlan {
    private Integer id;
    private Integer planId;
    private String slot;
    private Timestamp slotTime;
    //private String slot;
    private String accountName;
    private String roomName;
    private String subjectCode;
    private String subjectName;
    private String className;
    private String reason;
    private String accountName1;
    private String accountName2;
    private Integer result;
}
