package com.playstore.userservice.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public String handleUserExists(UserAlreadyExistsException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "register"; // redirect back to register page
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public String handleInvalidCredentials(InvalidCredentialsException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "login"; // redirect back to login page
    }

    // Handle validation errors when @Valid fails in controllers
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationErrors(MethodArgumentNotValidException ex, Model model) {
        StringBuilder errorMsg = new StringBuilder("Validation failed: ");
        ex.getBindingResult().getFieldErrors().forEach(error ->
            errorMsg.append(error.getDefaultMessage()).append(" ")
        );
        model.addAttribute("error", errorMsg.toString().trim());
        return "register"; // if it's for registration form
    }

    // Handle validation errors from persistence layer (Hibernate Validator)
    @ExceptionHandler(ConstraintViolationException.class)
    public String handleConstraintViolation(ConstraintViolationException ex, Model model) {
        StringBuilder errorMsg = new StringBuilder("Validation failed: ");
        ex.getConstraintViolations().forEach(violation ->
            errorMsg.append(violation.getMessage()).append(" ")
        );
        model.addAttribute("error", errorMsg.toString().trim());
        return "register"; // adjust if used elsewhere
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
}
