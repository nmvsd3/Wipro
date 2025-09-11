package com.playstore.ownerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

// Data Transfer Object (DTO)
@Data                       // Generates getters, setters, 
@NoArgsConstructor          // No-args constructor
@AllArgsConstructor         // All-args constructor
@Builder                    // Builder pattern support
public class ApplicationDTO {

    private Long id;                 // Unique ID of the application
    private String name;             // Application name
    private String description;      // Short description of the app
    private LocalDate releaseDate;   // Release date of the app
    private String version;          // Current version
    private String genre;            // App category/genre
    private Double rating;           // Average rating (nullable for safety)
    private Long downloadCount;      // Total downloads (nullable for safety)
    private boolean visible;         // Visibility status (true = shown, false = hidden)
}
