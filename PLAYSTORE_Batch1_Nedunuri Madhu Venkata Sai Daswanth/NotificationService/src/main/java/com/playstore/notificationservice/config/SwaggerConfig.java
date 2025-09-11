package com.playstore.notificationservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Marks this class as a configuration class for Spring
@Configuration
public class SwaggerConfig {

    // Defines a Swagger/OpenAPI configuration bean
    @Bean
    public OpenAPI customOpenAPI() {
        // Creates OpenAPI documentation with basic details
        return new OpenAPI()
                .info(new Info()
                        .title("Notification Service API") // API title
                        .version("1.0") // API version
                        .description("Handles email notifications for Playstore system")); // API description
    }
}
