package com.playstore.ownerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

// Main class for the Owner Service application
@SpringBootApplication   // Marks this as a Spring Boot application
@EnableFeignClients      // Enables Feign clients for calling other microservices
public class OwnerServiceApplication {

    public static void main(String[] args) {
        // Starts the Spring Boot application
        SpringApplication.run(OwnerServiceApplication.class, args);
    }
}
