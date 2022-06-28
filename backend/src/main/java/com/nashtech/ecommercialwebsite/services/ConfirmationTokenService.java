package com.nashtech.ecommercialwebsite.services;

import com.nashtech.ecommercialwebsite.data.entity.ConfirmationToken;
import java.util.Optional;

public interface ConfirmationTokenService {

    void saveConfirmationToken(ConfirmationToken confirmationToken);

    int setConfirmedAt(String token);

    Optional<ConfirmationToken> getToken(String token);

}
