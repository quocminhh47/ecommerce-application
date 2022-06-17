package com.nashtech.ecommercialwebsite.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "roles")
@Getter @Setter
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private  Integer id;

    @Column(name = "role")
    private String role;

    @Column(name = "description")
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "role")
    private Set<Account> accounts;

}
