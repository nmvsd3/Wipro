package com.playstore.notificationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

// Main class for the Notification Service application
@SpringBootApplication   // Marks this as a Spring Boot application
@EnableFeignClients      // Enables Feign clients for calling other microservices
public class NotificationServiceApplication {

    public static void main(String[] args) {
        // Starts the Spring Boot application
        SpringApplication.run(NotificationServiceApplication.class, args);
    }
}
