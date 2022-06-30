package com.nashtech.ecommercialwebsite;


import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EcommercialWebsiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommercialWebsiteApplication.class, args);
    }
}
