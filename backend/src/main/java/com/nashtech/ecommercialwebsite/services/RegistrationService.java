package com.nashtech.ecommercialwebsite.services;

import com.nashtech.ecommercialwebsite.dto.request.RegistrationRequest;

public interface RegistrationService {

    String register(RegistrationRequest request);
    String confirmToken(String token);
    String buildEmail(String name, String link);
}
