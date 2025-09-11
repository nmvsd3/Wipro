package com.playstore.ownerservice.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// Global exception handler for specific controllers
@ControllerAdvice(assignableTypes = {
        com.playstore.ownerservice.controller.OwnerController.class,
        com.playstore.ownerservice.controller.ApplicationController.class
})
public class GlobalExceptionHandler {

    // Handles exception when an owner already exists
    // Returns the "register" view with an error message
    @ExceptionHandler(OwnerAlreadyExistsException.class)
    public String handleOwnerExists(OwnerAlreadyExistsException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "register";
    }

    // Handles exception for invalid login credentials
    // Returns the "login" view with an error message
    @ExceptionHandler(InvalidCredentialsException.class)
    public String handleInvalidCredentials(InvalidCredentialsException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "login";
    }
}
