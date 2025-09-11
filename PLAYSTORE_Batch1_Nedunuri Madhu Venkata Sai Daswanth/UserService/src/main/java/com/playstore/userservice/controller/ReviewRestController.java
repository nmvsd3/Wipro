package com.playstore.userservice.controller;

import com.playstore.userservice.entity.Review;
import com.playstore.userservice.service.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// REST controller exposing review-related APIs
// This is consumed by Feign clients 
@RestController
@RequestMapping("/api/reviews")   // Base path for review APIs
public class ReviewRestController {

    private final ReviewService reviewService;

    // Constructor injection of ReviewService
    public ReviewRestController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // Get all reviews for a specific app
    // Called by Owner Service via Feign client
    @GetMapping("/{appId}")
    public List<Review> getReviews(@PathVariable Long appId) {
        return reviewService.getReviewsByApp(appId);
    }
}
