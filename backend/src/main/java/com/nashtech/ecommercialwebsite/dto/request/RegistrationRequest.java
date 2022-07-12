package com.nashtech.ecommercialwebsite.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
    @Size(min = 10, message = "Phone at least 10 numbers")
    @Size(max = 12, message = "Phone max 12 numbers")
    private final String phone;

    @NotBlank(message = "address is required")
    private final String address;
}
