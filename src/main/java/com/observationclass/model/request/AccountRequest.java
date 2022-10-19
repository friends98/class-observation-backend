package com.observationclass.model.request;

import com.observationclass.entity.Role;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
public class AccountRequest {
    private Integer id;
    @NotNull
    private String userName;
    @NotNull
    private String email;
    private Set<Role> roles = new HashSet<>();
}
