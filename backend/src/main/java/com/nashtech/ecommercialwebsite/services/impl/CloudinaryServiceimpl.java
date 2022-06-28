package com.nashtech.ecommercialwebsite.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.nashtech.ecommercialwebsite.dto.response.FileUploadResponse;
import com.nashtech.ecommercialwebsite.exceptions.InternalServerException;
import com.nashtech.ecommercialwebsite.exceptions.UnsupportedMediaException;
import com.nashtech.ecommercialwebsite.services.CloudinaryService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class CloudinaryServiceimpl implements CloudinaryService {

    Cloudinary cloudinary;
    private final Map<String, String> valueMap = new HashMap<>();

    public CloudinaryServiceimpl() {
        valueMap.put("cloud_name", "duoih0eqa");
        valueMap.put("api_key", "721754572565457");
        valueMap.put("api_secret", "ulgetjLzPb11FriByIR4_z3vSQ8");
        cloudinary = new Cloudinary(valueMap);
    }

    public FileUploadResponse upload(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) throw new UnsupportedMediaException("File is not exist!!");
        try {
            File file = convert(multipartFile);
            Map<?, ?> resultMap = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
            file.delete();
            return FileUploadResponse.builder()
                    .message("Success")
                    .url(resultMap.get("url").toString())
                    .build();
        } catch (IOException ioException) {
            throw new InternalServerException(ioException.getMessage());
        }

    }

    private File convert(MultipartFile multipartFile) {
        try {
            File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            FileOutputStream fOutputStream = new FileOutputStream(file);
            fOutputStream.write(multipartFile.getBytes());
            fOutputStream.close();
            return file;
        } catch (IOException e) {
            throw new InternalServerException(e.getMessage());
        }
    }

}
