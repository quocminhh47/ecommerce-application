package com.nashtech.ecommercialwebsite.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class FileUploadResponse {

    private String message;

    private String url;

}
