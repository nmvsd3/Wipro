package com.playstore.ownerservice.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

// Feign client to communicate with the User Service
@FeignClient(name = "user-service", contextId = "userClient")
public interface UserClient {

    // Calls the User Service to get a list of all user emails
    @GetMapping("/api/users/emails")
    List<String> getAllUserEmails();
}
