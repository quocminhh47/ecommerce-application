package com.nashtech.ecommercialwebsite.dto.request;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductUpdateRequest {

    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Min(value = 0, message = "Price should be positive")
    @NotNull(message = "price cannot be null")
    private Long price;

    @Min(value = 0, message = "Price should be positive")
    @NotNull(message = "quantity cannot be null")
    private Integer quantity;

    @NotBlank(message = "status is required")
    private String status;

    @NotBlank(message = "thumbnail is required")
    private String thumbnail;

    @Min(value = 0) @Max(value = 1)
    private Float discount;

    @NotNull(message = "hidden cannot be null")
    private Boolean hidden;

    @Min(value = 0, message = "guaranteeTime should > 0 month")
    private Integer guaranteeTime;

    @NotNull(message = "gender cannot be null")
    private Boolean gender;

    @NotNull(message = "isWaterProof cannot be null")
    private Boolean isWaterProof;

    @Min(value = 0)
    private Float size;

    @Min(value = 1)
    @NotNull
    private Integer brandId;

    private String description;

    List<ImageUpdateRequest> images;

}
