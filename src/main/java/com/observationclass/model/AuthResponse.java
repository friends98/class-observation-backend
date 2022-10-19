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
    private Set<Role> setRole;

    public AuthResponse(String userName, String accessToken, Set<Role> setRole) {
        this.userName = userName;
        this.accessToken = accessToken;
        this.tokenType="Bearer";
        this.setRole = setRole;
    }
}
