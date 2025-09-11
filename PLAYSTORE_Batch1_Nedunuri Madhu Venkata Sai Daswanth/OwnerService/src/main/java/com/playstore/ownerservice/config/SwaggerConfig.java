package com.playstore.ownerservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Marks this as a Spring configuration class
@Configuration
public class SwaggerConfig {

    // Defines the OpenAPI (Swagger) configuration for Owner Service
    @Bean
    public OpenAPI ownerServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Owner Service API") // Title of the API docs
                        .description("API documentation for Owner Service (Playstore clone)") // Short description
                        .version("1.0.0")); // API version
    }
}
