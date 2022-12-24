package com.observationclass.entity;

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
@Table(name="observation_review")
@Entity
public class ObservationReview extends CommonEntity{
    @Column(name="lesson_name")
    private String lessonName;
    @Column(name="advantage")
    private String advantage;
    @Column(name="disadvantage")
    private String disadvantage;
    @Column(name="comment")
    private String comment;
    @Column(name="total_point")
    private Integer totalPoint;
    @Column(name="status_id")
    private Integer status;
    @ManyToOne(fetch= FetchType.LAZY,optional = false)
    @JoinColumn(name="account_id")
    private Account account;
    @ManyToOne(fetch= FetchType.LAZY,optional = false)
    @JoinColumn(name="observation_slot_id")
    private ObservationSlot observationSlot;

    @OneToMany(mappedBy = "observationReview",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<ObservationDetail> observationDetails;

}
