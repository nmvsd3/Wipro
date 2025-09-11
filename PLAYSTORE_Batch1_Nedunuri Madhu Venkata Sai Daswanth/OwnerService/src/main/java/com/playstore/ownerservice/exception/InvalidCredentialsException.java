package com.playstore.ownerservice.exception;

// Custom exception for invalid login attempts
public class InvalidCredentialsException extends RuntimeException {

    // Passes the error message to the RuntimeException constructor
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
