package com.playstore.userservice.exception;

// Custom exception thrown when user login credentials are invalid
public class InvalidCredentialsException extends RuntimeException {

    // Constructor passes custom error message to the RuntimeException class
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
