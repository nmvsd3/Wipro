package com.playstore.ownerservice.dto; // owner adjust

import lombok.AllArgsConstructor;
import lombok.Getter;

// DTO for authentication responses
// Typically returned after a successful login, carrying a JWT or session token
@Getter
@AllArgsConstructor
public class AuthResponse {
    private String token; // Authentication token 
}
