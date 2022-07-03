package com.nashtech.ecommercialwebsite.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter @Setter
public class RegistrationRequest {

    @NotBlank(message = "firstName is required")
    private final String firstName;

    @NotBlank(message = "lastName is required")
    private final String lastName;

    @NotBlank(message = "email is required")
    private final String email;

    @NotBlank(message = "password is required")
    private final String password;

    @NotBlank(message = "phone is required")
    @Min(value = 10, message = "Phone at least 10 numbers")
    @Min(value = 12, message = "Phone max 12 numbers")
    private final String phone;

    @NotBlank(message = "address is required")
    private final String address;
}
