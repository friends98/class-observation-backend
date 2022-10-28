package com.observationclass.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="observation_slot")
@Entity
public class ObservationSlot extends CommonEntity{
    @Column(name="slot_time")
    private Timestamp slotTime;

    @ManyToOne(fetch=FetchType.LAZY,optional = false)
    @JoinColumn(name="slot_id",nullable = false)
    private Slot slot;

    @ManyToOne(fetch = FetchType.LAZY,optional=false)
    @JoinColumn(name="room_id",nullable = false)
    private Room room;

    @ManyToOne(fetch=FetchType.LAZY,optional = false)
    @JoinColumn(name="subject_id")
    private Subject subject;

    @Column(name="reason")
    private String reason;

    @ManyToOne(fetch=FetchType.LAZY,optional = false)
    @JoinColumn(name="account_id")
    private Account account;

    @ManyToOne(fetch=FetchType.LAZY,optional = false)
    @JoinColumn(name="account_id1")
    private Account account1;

    @ManyToOne(fetch=FetchType.LAZY,optional = false)
    @JoinColumn(name="account_id2")
    private Account account2;

    public ObservationSlot(Slot slot,Room room,Subject subject){
        this.slot=slot;
        this.room=room;
        this.subject=subject;
    }

    @ManyToOne(fetch = FetchType.LAZY,optional=false)
    @JoinColumn(name="plan_id")
    private ObservationPlan observationPlan;

//    @OneToMany(mappedBy = "observationSlot",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
//    private Set<ObservationReivew> observationReivews;

}
