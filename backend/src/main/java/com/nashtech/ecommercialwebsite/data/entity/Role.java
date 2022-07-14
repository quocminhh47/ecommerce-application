package com.nashtech.ecommercialwebsite.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "roles")
@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private  Integer id;

    @Column(name = "role")
    private String roleName;

    @Column(name = "description")
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "role",  cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Account> accounts;

}
