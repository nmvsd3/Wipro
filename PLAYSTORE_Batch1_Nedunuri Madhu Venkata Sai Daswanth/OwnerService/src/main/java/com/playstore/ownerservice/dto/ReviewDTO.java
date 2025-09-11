package com.playstore.ownerservice.dto;

import lombok.Data;


// Used to transfer review data between services 
@Data
public class ReviewDTO {
    private Long id;         // Unique ID of the review
    private Long appId;      // ID of the application being reviewed
    private String userName; // Name of the user who submitted the review
    private String comment;  // Review text
    private int rating;      // Rating score 
}
