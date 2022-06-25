package com.nashtech.ecommercialwebsite.dto.request;

import lombok.*;

import java.util.List;


@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductRequest {
    private String name;
    private Long price;
    private Integer quantity;
    private String status;
    private String description;
    private String thumbnail;
    private Float discount;
    private Integer guaranteeTime;
    private Boolean gender;
    private Boolean isWaterProof;
    private Float size;
    private Integer brandId;
    List<ProductImageRequest> images;
}
