package com.nashtech.ecommercialwebsite.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageUpdateRequest {

    @Min(value = 0)
    private int id;

    @NotBlank(message = "image is required")
    private String image;

}
