
package com.playstore.notificationservice.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

// Marks this class as a JPA entity (mapped to a database table)
@Entity
// Lombok annotations to reduce boilerplate code
@Getter @Setter              // Generates getters and setters
@NoArgsConstructor           // Generates a no-args constructor
@AllArgsConstructor          // Generates a constructor with all fields
@Builder                     // Provides builder pattern for object creation
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment primary key
    private Long id;

    private String recipient;   // Recipient email or user identifier
    private String subject;     // Notification subject
    private String body;        // Notification content/message
    private LocalDateTime createdAt; // Timestamp when notification was created
}
