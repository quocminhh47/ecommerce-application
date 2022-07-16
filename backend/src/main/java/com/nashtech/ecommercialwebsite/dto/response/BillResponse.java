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
public class BillResponse {

    private List<BillItemResponse> cartDetails = new ArrayList<>();

    private  int billId;

    private int status;

    private int priceTotal;

    private  String firstName;

    private  String lastName;

    private String phone;

    private String address;

    private String email;
}
