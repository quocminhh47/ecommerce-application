package com.nashtech.ecommercialwebsite.config;


import com.google.common.collect.Lists;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {
    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .servers(Lists.newArrayList(
                        new Server().url("http://localhost:8080")
                ))
                .info(new Info()
                        .title("Ecommerce website")
                        .description("This document is specified by Pham Minh Quoc")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Quoc Minh ")
                                .url("https://github.com/quocminhh47")
                                .email("quocminhh47@gmail.com"))
                        .termsOfService("TOC")
                        .license(new License().name("License").url("#"))
                );

    }
}
