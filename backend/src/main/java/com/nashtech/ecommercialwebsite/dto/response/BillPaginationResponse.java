package com.nashtech.ecommercialwebsite.dto.response;

import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BillPaginationResponse {

    List<BillDetailReponse> billContent = new ArrayList<>();

    private int pageNo;

    private int pageSize;

    private long totalElements;

    private int totalPages;

    private boolean last;

   // LoginStatusResponse loginStatusResponse = new LoginStatusResponse();
}
