package com.nashtech.ecommercialwebsite.config;


import com.google.common.collect.Lists;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
public class OpenApiConfiguration {

    private final String moduleName;
    private final String apiVersion;
    private final String serverUrl;

    public OpenApiConfiguration(
            @Value("${module-name:Module}") String moduleName,
            @Value("${api-version:v1}") String apiVersion,
            @Value("${app.server}") String serverUrl) {
        this.moduleName = moduleName;
        this.apiVersion = apiVersion;
        this.serverUrl = serverUrl;
    }

    @Bean
    public OpenAPI openApi() {
        final String securitySchemeName = "bearerAuth";
        final String apiTitle = String.format("%s API", StringUtils.capitalize(moduleName));

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(
                        new Components()
                                .addSecuritySchemes(securitySchemeName,
                                        new SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                )
                .info(new Info().title(apiTitle).version(apiVersion))

                .servers(Lists.newArrayList(
                        new Server().url(serverUrl)
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
