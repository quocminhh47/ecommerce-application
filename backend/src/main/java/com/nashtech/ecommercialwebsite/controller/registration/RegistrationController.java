package com.nashtech.ecommercialwebsite.controller.registration;

import com.nashtech.ecommercialwebsite.dto.request.RegistrationRequest;
import com.nashtech.ecommercialwebsite.services.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/api")
public class RegistrationController {
    private static final String USER_ROLE = "USER";
    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/registration")
    public String register( @Valid @RequestBody RegistrationRequest registrationRequest){
        return registrationService.register(registrationRequest, USER_ROLE);
    }

    @GetMapping(path = "/registration/confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }
}
