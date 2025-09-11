package com.playstore.ownerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data                       // Generates getters, setters, toString, equals, hashCode
@NoArgsConstructor          // No-args constructor
@AllArgsConstructor         // All-args constructor
public class NotificationDTO {
    private Long id;                 // Unique ID of the notification
    private String recipient;        // Recipient email or user
    private String subject;          // Notification subject
    private String body;             // Notification message content
    private LocalDateTime createdAt; // When the notification was created
}
