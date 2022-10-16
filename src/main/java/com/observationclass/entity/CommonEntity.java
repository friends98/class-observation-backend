package com.observationclass.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommonEntity {
    private Timestamp created_at;
    private Timestamp updated_at;
    private Integer deleted_flag;
}
