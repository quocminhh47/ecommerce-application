package com.nashtech.ecommercialwebsite.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageUpdateRequest {
    private int id;
    private String image;
    private String description;
}
