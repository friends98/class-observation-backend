package com.observationclass.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="criteria")

@Entity
public class Criteria extends CommonEntity{


    @Column(name="criteria_code")
    private String criteriaCode;

    @Column(name="criteria_name")
    private String criteriaName;
}
