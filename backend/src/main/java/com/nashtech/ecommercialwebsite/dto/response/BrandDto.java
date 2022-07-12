package com.nashtech.ecommercialwebsite.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BrandDto {

    private  Integer id;

    private String name;

    private String thumbnail;

    private String description;

    //LoginStatusResponse loginStatusResponse = new LoginStatusResponse();
}
