package com.playstore.userservice.client;

import com.playstore.userservice.dto.NotificationDTO; // DTO for notifications
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

// Feign client for communicating with the Notification Service
@FeignClient(name = "notification-service", url = "http://localhost:8083")
public interface NotificationClient {

    // Send a notification 
    @PostMapping("/notify/send")
    String sendNotification(@RequestParam("to") String to,
                            @RequestParam("subject") String subject,
                            @RequestParam("body") String body);

    // Get all notifications for a specific user by email
    @GetMapping("/notify/user/{email}")
    List<NotificationDTO> getNotifications(@PathVariable("email") String email);
}
