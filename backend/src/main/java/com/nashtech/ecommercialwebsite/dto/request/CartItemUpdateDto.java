package com.nashtech.ecommercialwebsite.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemUpdateDto {

    private int productId;

    private int productQuantity;

    @Override
    public String toString() {
        return "CartItemUpdateDto{" +
                "productId=" + productId +
                ", productQuantity=" + productQuantity +
                '}';
    }
}
