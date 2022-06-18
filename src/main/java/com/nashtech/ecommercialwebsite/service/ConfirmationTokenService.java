package com.nashtech.ecommercialwebsite.service;

import com.nashtech.ecommercialwebsite.entity.ConfirmationToken;

import java.util.Optional;

public interface ConfirmationTokenService {
    void saveConfirmationToken(ConfirmationToken confirmationToken);
    int setConfirmedAt(String token);
    Optional<ConfirmationToken> getToken(String token);
}
