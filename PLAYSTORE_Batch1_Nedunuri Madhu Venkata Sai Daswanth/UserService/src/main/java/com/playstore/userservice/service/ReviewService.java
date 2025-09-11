package com.playstore.userservice.service;

import com.playstore.userservice.client.NotificationClient;
import com.playstore.userservice.entity.Review;
import com.playstore.userservice.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

// Service layer for handling review-related logic
@Service
public class ReviewService {

    private final ReviewRepository repo;
    private final NotificationClient notificationClient;

    // Constructor injection
    public ReviewService(ReviewRepository repo, NotificationClient notificationClient) {
        this.repo = repo;
        this.notificationClient = notificationClient;
    }

    // Save a new review and send notification to the app owner
    public Review addReview(Review review) {
        Review saved = repo.save(review);

        // Notify owner about the new review
        // (Currently hardcoded email; in real implementation, fetch actual owner email via OwnerService)
        notificationClient.sendNotification(
                "owner@example.com",
                "New Review for App ID " + review.getAppId(),
                "User: " + review.getUserName() + " rated " + review.getRating() + "⭐ - " + review.getComment()
        );

        return saved;
    }

    // Get all reviews for a specific app
    public List<Review> getReviewsByApp(Long appId) {
        return repo.findByAppId(appId);
    }
}
