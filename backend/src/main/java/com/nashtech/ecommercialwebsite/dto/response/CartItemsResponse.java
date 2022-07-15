package com.nashtech.ecommercialwebsite.dto.response;

import lombok.*;

@Getter @Setter @Builder @ToString
@NoArgsConstructor @AllArgsConstructor
public class CartItemsResponse {

    //private CartDetailId cartItemId;

    private int productId;

    private String productName;

    private int productPrice;

    private int cartDetailQuantity;

    private String productThumbnail;

}
