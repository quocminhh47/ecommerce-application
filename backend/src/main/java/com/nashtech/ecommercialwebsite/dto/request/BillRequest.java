package com.nashtech.ecommercialwebsite.dto.request;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BillRequest {

    List<CartItemUpdateDto> cartDetails =  new ArrayList<>();

}
