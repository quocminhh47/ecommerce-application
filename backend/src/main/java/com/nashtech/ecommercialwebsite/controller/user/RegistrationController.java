package com.nashtech.ecommercialwebsite.controller.user;

import com.nashtech.ecommercialwebsite.dto.request.RegistrationRequest;
import com.nashtech.ecommercialwebsite.dto.response.RegistrationResponse;
import com.nashtech.ecommercialwebsite.dto.response.TokenResponse;
import com.nashtech.ecommercialwebsite.services.RegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Registration Resources",
        description = "Registration resources that provide the registration and mail verification feature")
@RestController
@RequestMapping("/v1/api")
public class RegistrationController {
    private static final String USER_ROLE = "USER";
    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register feature",
            description = "This create accounts for customers and send token to customer's email for verification")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "201", description = "OK - Successfully created account"),
            @ApiResponse( responseCode = "400",
                    description = "Bad Request - The request is invalid",
                    content = {@Content(examples = {@ExampleObject(value = "")})}),
            @ApiResponse(responseCode = "500",
                    description = "Internal Error - There were some error while processing in server",
                    content = {@Content(examples = {@ExampleObject()})})
    })
    public TokenResponse register(@Valid @RequestBody RegistrationRequest registrationRequest){
        return registrationService.register(registrationRequest, USER_ROLE);
    }

    @GetMapping(path = "/registration/confirm")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Gmail confirm to enable account",
            description = "This check the token is valid or not then active the customer's account" )
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "OK - Successfully confirmed"),
            @ApiResponse( responseCode = "400",
                    description = "Bad Request - The token is invalid",
                    content = {@Content(examples = {@ExampleObject(value = "")})}),
            @ApiResponse( responseCode = "404",
                    description = "Not found - The token is not exits",
                    content = {@Content(examples = {@ExampleObject(value = "")})}),
            @ApiResponse( responseCode = "404",
                    description = "Not found - The request resources was not found",
                    content = {@Content(examples = {@ExampleObject(value = "")})}),
            @ApiResponse(responseCode = "500",
                    description = "Internal Error - There were some error while processing in server",
                    content = {@Content(examples = {@ExampleObject()})})

    })
    public RegistrationResponse confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }
}
