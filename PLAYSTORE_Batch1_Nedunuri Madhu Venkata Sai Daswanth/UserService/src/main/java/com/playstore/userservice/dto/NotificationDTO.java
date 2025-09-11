package com.playstore.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDTO {
    private Long id;                 // Unique notification ID
    private String recipient;        // Email or user identifier
    private String subject;          // Notification subject/title
    private String body;             // Notification message content
    private LocalDateTime createdAt; // Timestamp when notification was created
}
