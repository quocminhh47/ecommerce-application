package com.nashtech.ecommercialwebsite.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "accounts")
@NoArgsConstructor
@Getter
@Setter
public class Account {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", unique = true)
    private String username; //username = email

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "password")
    private String password;

    @Column(name = "locked")
    private Boolean locked = false;

    @Column(name = "enabled")
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

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    Set<ProductImage> productImages;

    @JsonIgnore
    @OneToMany(mappedBy = "account")
    Set<Rating> ratings;


    public Account(String username,
                   String first_name,
                   String last_name,
                   String password,
                   Role role) {
        this.username = username;
        this.firstName = first_name;
        this.lastName = last_name;
        this.password = password;
        this.role = role;
    }
}