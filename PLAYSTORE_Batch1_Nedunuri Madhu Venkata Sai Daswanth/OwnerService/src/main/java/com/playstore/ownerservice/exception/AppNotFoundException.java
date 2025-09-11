package com.playstore.ownerservice.exception;

// Custom exception for when an application is not found
public class AppNotFoundException extends RuntimeException {

    // Passes the error message to the RuntimeException constructor
    public AppNotFoundException(String message) {
        super(message);
    }
}
