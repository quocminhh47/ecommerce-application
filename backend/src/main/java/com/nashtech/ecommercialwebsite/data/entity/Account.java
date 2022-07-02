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
@Getter @Setter
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
    private Boolean isNonLocked = true;

    @Column(name = "enabled")
    private Boolean enabled = false;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id", referencedColumnName = "id" )
    private Cart cart;

    @JsonIgnore
    @OneToMany(mappedBy = "account",  cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Bill> bills;


    @JsonIgnore
    @OneToMany(mappedBy = "account",  cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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

    public Account(Long id,
                   String username,
                   String firstName,
                   String lastName,
                   String password,
                   Role role) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.role = role;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", locked=" + isNonLocked +
                ", enabled=" + enabled +
                ", role=" + role +
                '}';
    }
}