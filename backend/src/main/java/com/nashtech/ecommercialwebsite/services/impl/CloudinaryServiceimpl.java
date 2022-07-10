package com.nashtech.ecommercialwebsite.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.nashtech.ecommercialwebsite.dto.response.FileUploadResponse;
import com.nashtech.ecommercialwebsite.exceptions.BadRequestException;
import com.nashtech.ecommercialwebsite.exceptions.InternalServerException;
import com.nashtech.ecommercialwebsite.services.CloudinaryService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Service
public class CloudinaryServiceimpl implements CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryServiceimpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public FileUploadResponse upload(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) throw new BadRequestException("File is not exist!!");
        try {
            File file = convert(multipartFile);
            Map<?, ?> resultMap = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
            file.delete();
            return FileUploadResponse.builder()
                    .message("Upload to Cloudinary success")
                    .url(resultMap.get("url").toString())
                    .build();
        } catch (IOException ioException) {
            throw new InternalServerException(ioException.getMessage(), ioException);
        }

    }

    private File convert(MultipartFile multipartFile) {
        try {
            File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            FileOutputStream fOutputStream = new FileOutputStream(file);
            fOutputStream.write(multipartFile.getBytes());
            fOutputStream.close();
            return file;
        } catch (IOException ioException) {
            throw new InternalServerException(ioException.getMessage(), ioException);
        }
    }

}
