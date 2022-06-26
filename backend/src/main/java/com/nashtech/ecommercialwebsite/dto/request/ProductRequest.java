package com.nashtech.ecommercialwebsite.dto.request;

import lombok.*;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductRequest {
    @NotNull(message = "Name cannot be null")
    private String name;

    @Min(value = 0, message = "Price should be positive")
    private Long price;

    @Min(value = 0, message = "Price should be positive")
    private Integer quantity;

    @NotBlank(message = "status is required")
    private String status;

    private String description;

    @NotBlank(message = "thumbnail is required")
    private String thumbnail;

    @Min(value = 0) @Max(value = 1)
    private Float discount;

    @Min(value = 0, message = "guaranteeTime should > 0 month")
    private Integer guaranteeTime;

    @NotNull(message = "gender cannot be null")
    private Boolean gender;

    @NotNull(message = "isWaterProof cannot be null")
    private Boolean isWaterProof;

    @Min(value = 0)
    private Float size;

    @Min(value = 1)
    private Integer brandId;

    List<ProductImageRequest> images;
}
