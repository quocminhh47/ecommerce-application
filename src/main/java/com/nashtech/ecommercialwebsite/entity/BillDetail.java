package com.nashtech.ecommercialwebsite.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "bill_detail")
@RequiredArgsConstructor
@Getter @Setter
public class BillDetail {
    @EmbeddedId
    private BillDetail_ID  id;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne
    @MapsId("bill_ID")
    @JoinColumn(name = "bill_id")
    private Bill bill;

    @ManyToOne
    @MapsId("product_ID")
    @JoinColumn(name = "product_id")
    private Product product;

}
