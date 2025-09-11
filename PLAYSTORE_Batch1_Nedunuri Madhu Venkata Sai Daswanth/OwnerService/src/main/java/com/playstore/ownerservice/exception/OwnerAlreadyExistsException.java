package com.playstore.ownerservice.exception;

// Custom exception thrown when trying to register an owner that already exists
public class OwnerAlreadyExistsException extends RuntimeException {

    // Passes the error message to the RuntimeException constructor
    public OwnerAlreadyExistsException(String message) {
        super(message);
    }
}
