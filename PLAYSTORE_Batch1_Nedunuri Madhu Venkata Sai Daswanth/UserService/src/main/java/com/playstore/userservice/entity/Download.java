package com.playstore.userservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

// Entity representing a record of a user downloading an app
@Entity
@Table(name = "downloads") // Maps to "downloads" table in the database
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Download {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment primary key
    private Long id; // Unique ID for each download record

    private String appName;   // Name of the downloaded app
    private String userEmail; // Email of the user who downloaded the app
    private String version;
    private LocalDateTime downloadedAt; // Timestamp when the app was downloaded
}
