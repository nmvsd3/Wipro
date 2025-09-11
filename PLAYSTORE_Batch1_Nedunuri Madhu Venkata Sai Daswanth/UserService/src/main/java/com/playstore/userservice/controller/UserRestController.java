package com.playstore.userservice.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.playstore.userservice.entity.User;
import com.playstore.userservice.service.UserService;

// REST controller exposing APIs for User Service
@RestController
public class UserRestController {

    private final UserService userService;

    // Constructor injection of UserService
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    // Endpoint to fetch all user emails
    // Used by Feign clients (e.g., Owner Service) for notifications
    @GetMapping("/api/users/emails")
    public List<String> getAllUserEmails() {
        return userService.getAllUsers()
                          .stream()
                          .map(User::getEmail) // extract only email field
                          .toList();
    }
}
