package com.observationclass.model.request;

import com.observationclass.entity.ObservationSlot;
import lombok.Data;

import java.util.Set;

@Data
public class ObservationPlanRequest {
    private Integer id;

    private Integer semesterId;
    private Integer campusId;
    private Integer departmentId;
//    private Integer accountId;
    private Integer planStatus;
//    private Integer roomId;
//    private Integer subjectId;
//    private Integer slotId;
    private Set<ObservationSlotRequest> observationSlotsRequest;

    

}
