package com.observationclass.model.request;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ObservationSlotRequest {
    private Integer id;
    private Timestamp slotTime;
    private Integer slotId;
    private Integer roomId;
    private String reason;
    private Integer accountId;
    private Integer subjectId;
    private Integer accountId1;
    private Integer accountId2;
}
