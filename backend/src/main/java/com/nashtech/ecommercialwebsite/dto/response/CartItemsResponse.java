package com.nashtech.ecommercialwebsite.dto.response;

import lombok.*;

@Getter @Setter @Builder @ToString
@NoArgsConstructor @AllArgsConstructor
public class CartItemsResponse {
    private int cartId;

    private int productId;

    private String productName;

    private Long productPrice;

    private int cartDetailQuantity;

    private String productThumbnail;

}
