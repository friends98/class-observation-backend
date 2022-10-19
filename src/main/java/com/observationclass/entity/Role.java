package com.observationclass.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.observationclass.model.ERole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name="role_name")
    private ERole roleName;

    @ManyToMany(mappedBy = "roles",cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Account> accounts =new HashSet<>();

}
