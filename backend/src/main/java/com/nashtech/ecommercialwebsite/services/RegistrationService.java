package com.nashtech.ecommercialwebsite.services;

import com.nashtech.ecommercialwebsite.dto.response.RegistrationResponse;
import com.nashtech.ecommercialwebsite.dto.request.RegistrationRequest;
import com.nashtech.ecommercialwebsite.dto.response.TokenResponse;

public interface RegistrationService {

    TokenResponse register(RegistrationRequest request, String roleName);

    RegistrationResponse confirmToken(String token);

    String buildEmail(String name, String link);
}
