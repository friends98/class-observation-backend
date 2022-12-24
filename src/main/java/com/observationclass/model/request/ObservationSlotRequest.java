package com.observationclass.model.request;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ObservationSlotRequest {
    private Integer id;

    private Integer accountId;

    private Integer subjectId;

    private String reason;

    private Timestamp slotTime;

    private Integer slotId;

    private Integer roomId;

    private String className;

    private Integer headTraining;

    private Integer headSubject;

    private Integer accountId1;

    private Integer accountId2;
}
