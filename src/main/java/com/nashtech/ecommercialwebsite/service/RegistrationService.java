package com.nashtech.ecommercialwebsite.service;

import com.nashtech.ecommercialwebsite.model.RegistrationRequest;

public interface RegistrationService {

    String register(RegistrationRequest request);
    String confirmToken(String token);
    String buildEmail(String name, String link);
}
