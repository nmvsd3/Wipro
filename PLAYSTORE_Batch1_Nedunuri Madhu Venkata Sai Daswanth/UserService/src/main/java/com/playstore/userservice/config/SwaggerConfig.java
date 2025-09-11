package com.playstore.userservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Swagger / OpenAPI configuration for User Service
@Configuration
public class SwaggerConfig {

    // Defines OpenAPI documentation bean
    @Bean
    public OpenAPI ownerServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("User Service API") // Title shown in Swagger UI
                        .description("API documentation for Owner Service (Playstore clone)") // Description
                        .version("1.0.0")); // API version
    }
}
