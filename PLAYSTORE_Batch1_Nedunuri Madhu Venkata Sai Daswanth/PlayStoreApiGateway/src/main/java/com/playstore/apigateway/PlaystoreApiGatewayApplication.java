package com.playstore.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

// Main class for the API Gateway application
@SpringBootApplication
public class PlaystoreApiGatewayApplication {

    // Entry point of the application
    public static void main(String[] args) {
        SpringApplication.run(PlaystoreApiGatewayApplication.class, args);
    }

    // Bean definition for RestClient builder (used to make REST calls to other services)
    @Bean
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }
}
