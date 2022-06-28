package com.nashtech.ecommercialwebsite;

import com.cloudinary.utils.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
@SpringBootApplication
//@EnableSwagger2
public class EcommercialWebsiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommercialWebsiteApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STANDARD);
        return modelMapper;
    }


}
