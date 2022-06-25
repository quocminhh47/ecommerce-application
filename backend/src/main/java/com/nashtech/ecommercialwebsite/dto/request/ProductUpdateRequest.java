package com.nashtech.ecommercialwebsite.dto.request;

import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductUpdateRequest {
    private String name;
    private Long price;
    private Integer quantity;
    private String status;
    private String thumbnail;
    private Float discount;
    private Boolean hidden;
    private Integer guaranteeTime;
    private Boolean gender;
    private Boolean isWaterProof;
    private Float size;
    private Integer brandId;
    private String description;
    List<ImageUpdateRequest> images;

}
