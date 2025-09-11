package com.playstore.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

// Main class for the User Service application
@SpringBootApplication       // Marks this as a Spring Boot application
@EnableFeignClients          // Enables Feign clients for inter-service communication
public class UserServiceApplication {

    // Entry point of the application
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}
