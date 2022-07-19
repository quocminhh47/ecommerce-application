package com.nashtech.ecommercialwebsite.dto.response;

import lombok.*;
import java.util.List;

@Getter @Setter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountResponse {

    List<UserAccountDto> userAccountContent;

    private int pageNo;

    private int pageSize;

    private long totalElements;

    private int totalPages;

    private boolean last;

//    LoginStatusResponse loginStatus = new LoginStatusResponse();
}
