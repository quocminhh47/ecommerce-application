package com.nashtech.ecommercialwebsite.dto.response;

import lombok.*;

import java.util.List;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    private List<ProductDto> productContent;

    private int pageNo;

    private int pageSize;

    private long totalElements;

    private int totalPages;

    private boolean last;

    private String currentUser;

    @Override
    public String toString() {
        return "ProductResponse{" +
                "productContent=" + productContent +
                ", pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", totalElements=" + totalElements +
                ", totalPages=" + totalPages +
                ", last=" + last +
                ", currentUser='" + currentUser + '\'' +
                '}';
    }
}
