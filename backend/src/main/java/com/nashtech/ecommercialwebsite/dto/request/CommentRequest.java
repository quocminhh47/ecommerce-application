package com.nashtech.ecommercialwebsite.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
@NoArgsConstructor
public class CommentRequest {

    @NotBlank(message = "Comment message can not be null")
    private String message;
}
