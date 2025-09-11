package com.playstore.userservice.dto;

import lombok.*;
import java.time.LocalDate;

// Data Transfer Object (DTO) for application details (used in User Service)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationDTO {
    private Long id;               // Unique ID of the application
    private String name;           // App name
    private String description;    // Short description
    private LocalDate releaseDate; // Release date
    private String version;        // Current version
    private String genre;          // App category/genre
    private Double rating;         // Average rating (nullable for safety)
    private Long downloadCount;    // Total downloads (nullable for safety)
    private boolean visible;       // Visibility flag (true = listed, false = hidden)
    //private String imageUrl;       // App image/thumbnail URL
    private boolean downloaded; // ✅ true if user has already downloaded
    private boolean updateAvailable; // true if owner announced a new update

}
