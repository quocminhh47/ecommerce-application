package com.nashtech.ecommercialwebsite.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter @Setter
public class BillDetailId implements Serializable {
    @Column(name="bill_id")
    Integer billId;
    @Column(name = "product_id")
    String productId;

}
