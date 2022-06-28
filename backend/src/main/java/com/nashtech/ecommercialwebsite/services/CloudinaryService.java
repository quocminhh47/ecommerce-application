package com.nashtech.ecommercialwebsite.services;

import com.nashtech.ecommercialwebsite.dto.response.FileUploadResponse;
import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {

     FileUploadResponse upload(MultipartFile multipartFile);

}
