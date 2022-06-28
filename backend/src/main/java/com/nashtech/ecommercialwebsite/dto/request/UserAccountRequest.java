package com.nashtech.ecommercialwebsite.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountRequest {

    private String id;

    private String userName;

    private String firstName;

    private String lastName;

    Boolean enabled;

    Boolean locked;
}
