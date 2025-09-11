package com.playstore.userservice.exception;

// Custom exception thrown when a user tries to register with an email/phone that already exists
public class UserAlreadyExistsException extends RuntimeException {

    // Constructor passes custom error message to the RuntimeException class
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
