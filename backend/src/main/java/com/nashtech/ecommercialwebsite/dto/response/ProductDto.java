package com.nashtech.ecommercialwebsite.dto.response;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Integer id;
    private String name;
    private String brandName;
    private Long price;
    private String thumbnail;

}
