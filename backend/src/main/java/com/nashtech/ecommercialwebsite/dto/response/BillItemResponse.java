package com.nashtech.ecommercialwebsite.dto.response;

import lombok.*;

@ToString
@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class BillItemResponse {

    private int productId;

    private String productName;

    private int productQuantity;

    private int productPrice;

}
