package com.observationclass.model.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class AccountResponse {
    private Integer id;
    private String userName;
    private String email;
    private String campusName;
}
