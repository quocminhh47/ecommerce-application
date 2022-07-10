package com.nashtech.ecommercialwebsite.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter
public class UserRequest {

    @NotNull(message = "User enabled status is required")
    private  Boolean isEnabled;

    @NotNull(message = "User isNonLocked status is required")
    private  Boolean isNonLocked;
}
