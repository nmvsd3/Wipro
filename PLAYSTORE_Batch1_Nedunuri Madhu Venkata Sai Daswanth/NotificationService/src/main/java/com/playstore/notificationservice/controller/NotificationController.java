package com.playstore.notificationservice.controller;

import com.playstore.notificationservice.entity.Notification;
import com.playstore.notificationservice.service.EmailService;

import java.util.List;

import org.springframework.web.bind.annotation.*;

// Marks this class as a REST controller to handle HTTP requests
@RestController
@RequestMapping("/notify") // Base URL for all notification endpoints
public class NotificationController {

    private final EmailService emailService;

    // Constructor injection of EmailService
    public NotificationController(EmailService emailService) {
        this.emailService = emailService;
    }

    // Endpoint to send a notification for mail
    @PostMapping("/send")
    public String sendNotification(@RequestParam String to,
                                   @RequestParam String subject,
                                   @RequestParam String body) {
        emailService.sendEmail(to, subject, body);
        return "Notification sent to " + to;
    }

    // Endpoint to get all notifications for a specific user for mail
    @GetMapping("/user/{email}")
    public List<Notification> getUserNotifications(@PathVariable String email) {
        return emailService.getUserNotifications(email);
    }
}
