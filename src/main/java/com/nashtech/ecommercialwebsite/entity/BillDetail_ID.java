package com.nashtech.ecommercialwebsite.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter @Setter
public class BillDetail_ID implements Serializable {
    @Column(name="bill_id")
    Integer bill_ID;
    @Column(name = "product_id")
    String product_ID;

}
