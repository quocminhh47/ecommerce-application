package com.nashtech.ecommercialwebsite.dto.response;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {

    private int cartId;

    private int productId;

    private Boolean actived;

    private int quantity;
}
