package com.nashtech.ecommercialwebsite.data.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDetailId implements Serializable {

    @Column(name = "product_id")
    Integer productID;

    @Column(name = "cart_id")
    Integer cartID;


}
