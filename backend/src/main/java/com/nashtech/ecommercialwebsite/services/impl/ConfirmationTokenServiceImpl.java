package com.nashtech.ecommercialwebsite.services.impl;

import com.nashtech.ecommercialwebsite.data.entity.ConfirmationToken;
import com.nashtech.ecommercialwebsite.data.repository.ConfirmationTokenRepository;
import com.nashtech.ecommercialwebsite.services.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public void saveConfirmationToken(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.save(confirmationToken);
    }

    @Override
    public int setConfirmedAt(String token) {
        return confirmationTokenRepository.updateConfirmedAt(
                token,
                LocalDateTime.now()
        );
    }

    @Override
    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }
}
