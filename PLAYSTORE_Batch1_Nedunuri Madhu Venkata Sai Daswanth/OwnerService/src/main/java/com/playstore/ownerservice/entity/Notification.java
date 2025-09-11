package com.playstore.ownerservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

// JPA entity representing a notification in the Owner Service
@Entity
@Table(name = "notifications") // Maps to "notifications" table in the database
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment primary key
    private Long id; // Unique notification ID

    private String message; // Notification message content

    private LocalDateTime createdAt; // Timestamp when notification was created

    // Many notifications can belong to one owner
    @ManyToOne(fetch = FetchType.LAZY) // Lazy loading for performance
    @JoinColumn(name = "owner_id")     // Foreign key column referencing Owner
    private Owner owner;
}
