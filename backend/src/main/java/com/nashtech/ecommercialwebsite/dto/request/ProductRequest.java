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
@ToString @Builder
public class ProductRequest {

    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank ")
    private String name;

    @Min(value = 1, message = "Price should be positive")
    @NotNull(message = "Price cannot be blank or null")
    private Long price;

    @Min(value = 0, message = "Price should be positive")
    @NotNull(message = "quantity cannot be blank or null")
    private Integer quantity;

    @NotBlank(message = "status is required")
    private String status;

    private String description;

    @NotBlank(message = "thumbnail is required")
    @NotNull(message = "thumbnail cannot be null")
    private String thumbnail;

    @Min(value = 0) @Max(value = 1)
    @NotNull(message = "discount cannot be blank or null")
    private Float discount;

    @Min(value = 0, message = "guaranteeTime should > 0 month")
    @NotNull(message = "guaranteeTime cannot be blank or null")
    private Integer guaranteeTime;

    @NotNull(message = "gender cannot be null")
    private Boolean gender;

    @NotNull(message = "isWaterProof cannot be null")
    private Boolean isWaterProof;

    @Min(value = 1)
    @NotNull(message = "size cannot be null")
    private Float size;

    @Min(value = 1)
    @NotNull(message = "Brand is required")
    private Integer brandId;

    List<ProductImageRequest> images;
}
