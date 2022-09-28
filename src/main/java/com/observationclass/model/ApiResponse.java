package com.observationclass.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApiResponse {
    private String status;
    private String message;
    private Object items;

    public ApiResponse(String status, String message, Object items) {
        this.setStatus(status);
        this.setMessage(message);
        this.setItems(items);
    }

}
