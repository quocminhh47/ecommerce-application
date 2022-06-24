package com.nashtech.ecommercialwebsite.service;

import com.nashtech.ecommercialwebsite.dto.request.RegistrationRequestDto;

public interface RegistrationService {

    String register(RegistrationRequestDto request);
    String confirmToken(String token);
    String buildEmail(String name, String link);
}
