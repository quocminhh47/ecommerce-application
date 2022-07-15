package com.nashtech.ecommercialwebsite.data.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "cart_detail")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDetail {

    @EmbeddedId
    CartDetailId id;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "actived")
    private Boolean actived = true;

    @ManyToOne
    @MapsId("cartID")
    @JoinColumn(name = "cart_id")
    Cart cart;

    @ManyToOne
    @MapsId("productID")
    @JoinColumn(name = "product_id")
    Product product;


}