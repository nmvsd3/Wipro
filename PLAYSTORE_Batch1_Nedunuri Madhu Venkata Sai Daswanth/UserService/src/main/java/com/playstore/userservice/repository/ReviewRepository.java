package com.playstore.userservice.repository;

import com.playstore.userservice.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Repository for Review entity
// Provides CRUD and custom query methods
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // Find all reviews for a specific app
    List<Review> findByAppId(Long appId);
}
