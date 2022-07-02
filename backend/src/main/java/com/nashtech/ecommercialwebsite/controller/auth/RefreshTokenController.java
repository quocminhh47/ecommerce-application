package com.nashtech.ecommercialwebsite.controller.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nashtech.ecommercialwebsite.data.entity.Account;
import com.nashtech.ecommercialwebsite.data.entity.Role;
import com.nashtech.ecommercialwebsite.data.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
public class RefreshTokenController {

    private  final UserRepository userRepository;

    public RefreshTokenController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer "))
            try {
            String refreshToken = authorizationHeader.substring("Bearer ".length());
            Algorithm algorithm = Algorithm.HMAC256("somethingverysecrectkeygoeshere".getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(refreshToken);
            String username = decodedJWT.getSubject();
            Account account = userRepository.findAccountByUsername(username).get();
            List<Role> authorities = new ArrayList<>();
            authorities.add(account.getRole());
            String accessToken = JWT.create()
                    .withSubject(account.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 15 * 60 * 1000))
                    .withIssuer(request.getRequestURL().toString())
                    //TODO : fix that line 53
                    .withClaim(
                            "roles",
                            authorities)
                    .sign(algorithm);
            Map<String, String> tokens = new HashMap<>();
            tokens.put("access_token", accessToken);
            tokens.put("refesh_token", refreshToken);
            response.setContentType(APPLICATION_JSON_VALUE);
        } catch (Exception e) {
            log.error("Error logging in: {}", e.getMessage());
            response.setHeader("error", e.getMessage());
            response.setStatus(FORBIDDEN.value());
            Map<String, String> errors = new HashMap<>();
            errors.put("error_message", e.getMessage());
            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), errors);
        }
    }
}
