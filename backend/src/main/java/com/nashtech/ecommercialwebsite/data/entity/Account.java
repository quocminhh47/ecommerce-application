package com.nashtech.ecommercialwebsite.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "accounts")
@NoArgsConstructor
@Getter @Setter @Builder
@AllArgsConstructor
public class Account {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "email", unique = true)
    private String username; //username = email

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "password")
    private String password;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

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


    public Account(Integer id,
                   String username, String firstName,
                   String lastName, String password,
                   String phone, String address, Role role) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.role = role;
    }

    public Account( String username, String firstName,
                   String lastName, String password,
                   String phone, String address, Role role) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(getId(), account.getId()) && Objects.equals(getUsername(), account.getUsername()) && Objects.equals(getFirstName(), account.getFirstName()) && Objects.equals(getLastName(), account.getLastName()) && Objects.equals(getPassword(), account.getPassword()) && Objects.equals(getPhone(), account.getPhone()) && Objects.equals(getAddress(), account.getAddress()) && Objects.equals(getIsNonLocked(), account.getIsNonLocked()) && Objects.equals(getEnabled(), account.getEnabled()) && Objects.equals(getRole(), account.getRole()) && Objects.equals(getCart(), account.getCart());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getFirstName(), getLastName(), getPassword(), getPhone(), getAddress(), getIsNonLocked(), getEnabled(), getRole(), getCart());
    }
}