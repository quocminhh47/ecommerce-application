package com.nashtech.ecommercialwebsite.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "carts")
@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private  Integer id;

    @JsonIgnore
    @OneToOne(mappedBy = "cart")
    private Account account;

    @JsonIgnore
    @OneToMany(mappedBy = "cart" , fetch = FetchType.EAGER,  cascade = CascadeType.ALL)
//    private Set<CartDetail> cartDetails;
    private List<CartDetail> cartDetails;

 }
