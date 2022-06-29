package com.nashtech.ecommercialwebsite.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfiguration {

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> valueMap = new HashMap<>();
        valueMap.put("cloud_name", "duoih0eqa");
        valueMap.put("api_key", "721754572565457");
        valueMap.put("api_secret", "ulgetjLzPb11FriByIR4_z3vSQ8");
        return new Cloudinary(valueMap);
    }
}
