package com.nashtech.ecommercialwebsite.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "carts")
@Getter @Setter
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private  Integer id;

    @Column(name = "status")
    private Integer status;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    @Column(name = "create_date")
    private Date createDate;

    @JsonIgnore
    @OneToOne(mappedBy = "cart", fetch = FetchType.LAZY)
    private Account account;

    @JsonIgnore
    @OneToMany(mappedBy = "cart")
    private Set<CartDetail> cartDetails;

 }
