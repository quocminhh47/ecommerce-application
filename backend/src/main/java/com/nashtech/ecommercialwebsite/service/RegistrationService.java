package com.nashtech.ecommercialwebsite.service;

import com.nashtech.ecommercialwebsite.dto.RegistrationRequest;

public interface RegistrationService {

    String register(RegistrationRequest request);
    String confirmToken(String token);
    String buildEmail(String name, String link);
}
