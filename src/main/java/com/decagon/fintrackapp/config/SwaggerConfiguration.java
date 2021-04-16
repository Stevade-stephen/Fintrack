package com.decagon.fintrackapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


import java.util.Collections;

@Configuration
@EnableSwagger2
class SwaggerConfig {
    @Bean
    public Docket swaggerConfiguration(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/fintrack/**"))
                .apis(RequestHandlerSelectors.basePackage("com.decagon.fintrackapp.controller"))
                .build()
                .apiInfo(apiDetails());
    }

    private ApiInfo apiDetails() {
        return new ApiInfo(
                "FintrackApp API", " Fintrack API model", "1.0", "Free to use",
                new springfox.documentation.service.Contact("FintrackApp", "http://localhost:8080", "qfg@g.com"),
                "API License", "http://localhost:8080",
                Collections.emptyList());
    }
}
