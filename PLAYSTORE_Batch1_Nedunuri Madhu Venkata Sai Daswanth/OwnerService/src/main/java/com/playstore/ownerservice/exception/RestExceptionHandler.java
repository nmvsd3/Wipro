package com.playstore.ownerservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

// Global exception handler for REST controllers
@RestControllerAdvice(assignableTypes = {
        com.playstore.ownerservice.controller.ApplicationRestController.class
})
public class RestExceptionHandler {

    // Handles AppNotFoundException and returns 404 Not Found with error message
    @ExceptionHandler(AppNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleAppNotFound(AppNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage()));
    }

    // Handles any other unexpected exception and returns 500 Internal Server Error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Unexpected error: " + ex.getMessage()));
    }
}
