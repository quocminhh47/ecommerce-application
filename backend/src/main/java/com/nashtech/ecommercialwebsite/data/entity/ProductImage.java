package com.nashtech.ecommercialwebsite.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "product_image")
@NoArgsConstructor
@Getter @Setter
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image")
    private String image;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id")
    @Getter(AccessLevel.NONE)
    private Product product;


}
