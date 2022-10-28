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
@Table(name="observation_detail")
@Entity
public class ObservationDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    @Column(name="code")
    private String code;
    @Column(name="name")
    private String name;
    @Column(name="point")
    private Integer point;

    @ManyToOne(fetch = FetchType.LAZY,optional=false)
    @JoinColumn(name="observation_review_id")
    private ObservationReview observationReview;

}
