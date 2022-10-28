package com.observationclass.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="observation_plan")
@Entity
public class ObservationPlan extends CommonEntity {

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="semester_id",nullable = false)
    private Semester semester;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "campus_id", nullable = false)
    private Campus campus;

    @ManyToOne(fetch=FetchType.LAZY,optional = false)
    @JoinColumn(name="department_id",nullable = false)
    private Department department;

    @OneToMany(mappedBy = "observationPlan",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<ObservationSlot> observationSlots;

    @Column(name = "plan_status", columnDefinition = "integer DEFAULT 0")
    private Integer planStatus=0;
}
