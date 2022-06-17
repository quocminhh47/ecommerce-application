package com.nashtech.ecommercialwebsite.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "accounts")
@NoArgsConstructor
@Getter @Setter
public class Account {

    public Account(String username,
                   String first_name,
                   String last_name,
                   String password,
                   Role role) {
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.password = password;
        this.role = role;
    }

    @Id
    @Column(name = "email")
    private String username;
    private String first_name;
    private String last_name;
    private String password;
    private Boolean locked = false;
    private Boolean enabled = false;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @JsonIgnore
    @OneToOne()
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private Cart cart;

    @JsonIgnore
    @OneToMany(mappedBy = "account")
    private Set<Bill> bills;




}