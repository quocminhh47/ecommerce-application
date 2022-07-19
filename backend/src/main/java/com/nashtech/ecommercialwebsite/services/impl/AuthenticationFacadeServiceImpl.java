package com.nashtech.ecommercialwebsite.services.impl;

import com.nashtech.ecommercialwebsite.services.AuthenticationFacadeService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationFacadeServiceImpl implements AuthenticationFacadeService {
    @Override
    public String getCurentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
