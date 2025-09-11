package com.playstore.userservice.entity;

import jakarta.persistence.*;
import lombok.*;

// Entity representing a review submitted by a user for an application
@Entity
@Table(name = "reviews") // Maps to "reviews" table in the database
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment primary key
    private Long id;          // Unique ID of the review

    private Long appId;       // ID of the app being reviewed (links to Application)
    private String userName;  // Name of the user who submitted the review
    private String comment;   // Review text/comment
    private int rating;       // Rating value (e.g., 1–5 stars)
}
