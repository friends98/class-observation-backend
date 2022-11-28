package com.observationclass.model.response;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class SearchObservationSlotResponse {
    private Integer id;
    private Integer planId;
    private Integer slotId;
    private Timestamp slotTime;
    //private String slot;
    private Integer accountId;
    private Integer roomId;
    private Integer subjectId;
    private String subjectName;
    private String className;
    private String reason;
    private Integer accountId1;
    private Integer accountId2;
    private Integer result;

}
