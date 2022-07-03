package com.nashtech.ecommercialwebsite.dto.response;

import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private List<CartItemsResponse>  cartDetails = new ArrayList<>();

    private int priceTotal;

    private String phone;

    private String address;
}
