package com.nashtech.ecommercialwebsite.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class TokenResponse {

    private String message;

    private String token;
}
