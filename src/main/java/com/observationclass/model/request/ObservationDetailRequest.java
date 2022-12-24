package com.observationclass.model.request;

import lombok.Data;

@Data
public class ObservationDetailRequest {
    private Integer id;
    private String code;
    private String name;
    private Integer point;
}
