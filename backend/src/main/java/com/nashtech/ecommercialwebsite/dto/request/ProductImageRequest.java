package com.nashtech.ecommercialwebsite.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageRequest {

    @NotBlank(message = "image is required")
    private String image;
}
