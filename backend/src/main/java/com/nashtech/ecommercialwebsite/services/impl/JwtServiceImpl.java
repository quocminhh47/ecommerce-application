package com.nashtech.ecommercialwebsite.services.impl;

import com.nashtech.ecommercialwebsite.services.JwtService;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.servlet.http.HttpServletRequest;

@Service
public class JwtServiceImpl implements JwtService {
    final private String jwtSecret;

    final private int jwtExpirationMs;


    public JwtServiceImpl(@Value("${security.app.jwtSecret}") final String jwtSecret,
                          @Value("${security.app.jwtExpirationMs}") final int jwtExpirationMs) {
        this.jwtSecret = jwtSecret;
        this.jwtExpirationMs = jwtExpirationMs;
    }

    @Override
    public String parseJwt(HttpServletRequest request) {
        String headerContent = request.getHeader("Authorization");

        if (StringUtils.hasLength(headerContent) && headerContent.startsWith("Bearer ")) {
            return headerContent.substring(7, headerContent.length()); //7 is length of "Bearer "
        }
        return null;
    }

    @Override
    public String getUsernameFromToken(String jwtToken) {
        return Jwts.parser()
                .setSigningKey(jwtSecret) //secrect key
                .parseClaimsJws(jwtToken) //Jwt token which need to be parse
                .getBody() //take body content - this include username
                .getSubject(); //this is username
    }
}
