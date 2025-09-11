package com.playstore.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


// Returned after successful login, carries the JWT token
@Getter
@AllArgsConstructor
public class AuthResponse {
    private String token; // Authentication token 
}
