package com.nashtech.ecommercialwebsite.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BillDetailReponse {

    private  String username;

    private int billId;

    private  int status;

    private Date createDate;

    private  int priceTotal;
}
