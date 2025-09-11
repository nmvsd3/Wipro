package com.playstore.userservice.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.playstore.userservice.client.NotificationClient;
import com.playstore.userservice.dto.AuthRequest;
import com.playstore.userservice.dto.AuthResponse;
import com.playstore.userservice.dto.NotificationDTO;
import com.playstore.userservice.entity.User;
import com.playstore.userservice.security.JwtUtil;
import com.playstore.userservice.service.UserService;

// Controller for handling user registration, login, dashboard, and session management
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final NotificationClient notificationClient;

    // Constructor injection of dependencies
    public UserController(UserService userService,
                          JwtUtil jwtUtil,
                          NotificationClient notificationClient) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.notificationClient = notificationClient;
    }

    // Show registration form
    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // Handle user registration with validation
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") User user,
                           BindingResult result,
                           Model model) {
        if (result.hasErrors()) {
            // Collect validation error messages
            StringBuilder errorMsg = new StringBuilder();
            result.getFieldErrors().forEach(error ->
                errorMsg.append(error.getDefaultMessage()).append(" ")
            );
            model.addAttribute("error", errorMsg.toString().trim());
            return "register";
        }

        try {
            userService.register(user);
            model.addAttribute("success", "Registered successfully. Please login.");
            return "login";
        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            return "register";
        }
    }

    // Show login form
    @GetMapping("/login")
    public String showLogin(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    // Handle login (stores user in session + generates JWT)
    @PostMapping("/login")
    public String login(@RequestParam String emailOrPhone,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        try {
            User user = userService.login(emailOrPhone, password);

            // Save user in session
            session.setAttribute("user", user);

            // Generate JWT token and store in session
            String token = jwtUtil.generateToken(user.getEmail(), "ROLE_USER");
            session.setAttribute("token", token);

            return "redirect:/user/dashboard";
        } catch (RuntimeException ex) {
            model.addAttribute("error", "Invalid credentials");
            return "login";
        }
    }

    // Show dashboard (requires user session)
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/user/login"; // Redirect to login if session expired
        }

        // Fetch user notifications from Notification Service
        List<NotificationDTO> notifications = notificationClient.getNotifications(user.getEmail());

        model.addAttribute("user", user);
        model.addAttribute("notifications", notifications);

        return "dashboard";
    }

    // Logout (clear user session)
    @GetMapping("/logout")
    public String logout(HttpSession session, Model model) {
        session.invalidate(); // clear all session data
        model.addAttribute("message", "Logged out successfully.");
        return "login";
    }

    // REST API login (for Postman/mobile apps) → returns JWT
    @PostMapping("/api/login")
    @ResponseBody
    public AuthResponse apiLogin(@RequestBody AuthRequest request) {
        User user = userService.login(request.getEmailOrPhone(), request.getPassword());
        String token = jwtUtil.generateToken(user.getEmail(), "ROLE_USER");
        return new AuthResponse(token);
    }
}
