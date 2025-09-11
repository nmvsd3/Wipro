package com.playstore.userservice.dto;

import lombok.Getter;
import lombok.Setter;

// DTO for authentication requests (login input)
// Typically used when a user attempts to log in
@Getter
@Setter
public class AuthRequest {
    private String emailOrPhone; // User identifier (can be email or phone number)
    private String password;     // User password
}
