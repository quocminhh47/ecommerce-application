package com.nashtech.ecommercialwebsite.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@Configuration
//@EnableSwagger2
@ConditionalOnExpression("${swagger.enable}")
public class Swagger2Configuration {
   /* @Bean
    public Docket swaggerConfiguration() {
        //Return a prepared Docket instance
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/api/*"))
                .apis(RequestHandlerSelectors.basePackage("com.nashtech"))
                .build();
    }*/

    @Bean
    public Docket userRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("User Module")//Module name
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.nashtech.ecommercialwebsite.controller"))//Scanned controller path
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("xxx project development interface document")//Interface document title
                .description("This document is only used by the leaders and developers of the development technology team")//Description
                .termsOfServiceUrl("http://www.baidu.com/")//Related URL
                .contact(new Contact("back-end development","http://www.xxx.com/","XXXXXX7805@qq.com"))//Author's email, etc.
                .version("1.0")//version number
                .build();
    }
}
