package com.observationclass.model;

import com.observationclass.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@AllArgsConstructor
@Data
public class AuthResponse {
    private String userName;
    private String accessToken;
    private String tokenType;
    private Integer campusId;
    private Integer userId;
    private Set<Role> setRole;

    public AuthResponse(String userName, String accessToken,Integer campusId,Integer userId, Set<Role> setRole) {
        this.userName = userName;
        this.accessToken = accessToken;
        this.tokenType="Bearer";
        this.campusId=campusId;
        this.userId=userId;
        this.setRole = setRole;
    }
}
