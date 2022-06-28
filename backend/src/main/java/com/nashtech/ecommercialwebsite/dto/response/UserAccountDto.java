package com.nashtech.ecommercialwebsite.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountDto {

    private Long id;

    @JsonProperty("email")
    @NotBlank(message = "Email is required")
    private String userName;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotNull(message ="Enabled status is required")
    Boolean enabled;

    @NotNull(message ="Locked status is required")
    Boolean locked;

    @Override
    public String toString() {
        return "UserAccountDto{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", enabled=" + enabled +
                ", locked=" + locked +
                '}';
    }
}
