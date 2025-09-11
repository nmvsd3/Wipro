package com.playstore.ownerservice.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AuthRequest {
    private String emailOrPhone; // User identifier (can be email or phone number)
    private String password;     // User password
}
