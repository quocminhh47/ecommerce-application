package com.nashtech.ecommercialwebsite.services;

import javax.servlet.http.HttpServletRequest;

public interface JwtService {
    String parseJwt(HttpServletRequest request);
    String getUsernameFromToken(String jwtToken);
}
