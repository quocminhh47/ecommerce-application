package com.nashtech.ecommercialwebsite.dto.response;

import com.nashtech.ecommercialwebsite.data.entity.ProductImage;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class SingleProductResponse {

    private Integer id;

    private String name;

    private Long price;

    private Integer quantity;

    private String status;

    private String description;

    private Integer guaranteeTime;

    private Boolean gender;

    private Boolean isWaterProof;

    private Float size;

    private String brandName;

    List<ProductImage> productImages = new ArrayList<>();

}
