package com.nashtech.ecommercialwebsite.data.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "bill_detail")
@RequiredArgsConstructor
@Getter @Setter
public class BillDetail {
    @EmbeddedId
    private BillDetailId id;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price")
    private Integer price;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @MapsId("billId")
    @JoinColumn(name = "bill_id")
    private Bill bill;

    @MapsId("productId")
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
