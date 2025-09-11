package com.playstore.userservice.controller;

import com.playstore.userservice.entity.Review;
import com.playstore.userservice.service.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// Controller for handling user reviews (web views, not REST)
@Controller
@RequestMapping("/user/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    // Constructor injection of ReviewService
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // Add a new review (submitted via Thymeleaf form)
    @PostMapping("/add")
    public String addReview(@RequestParam Long appId,
                            @RequestParam String userName,
                            @RequestParam String comment,
                            @RequestParam int rating,
                            Model model) {
        // Validate rating range
        if (rating < 1 || rating > 5) {
            model.addAttribute("error", "Rating must be between 1 and 5.");
            return "user-apps"; // return to apps page with error
        }

        // Build Review object from form data
        Review review = Review.builder()
                .appId(appId)
                .userName(userName)
                .comment(comment)
                .rating(rating)
                .build();

        // Save review using service
        reviewService.addReview(review);

        // Add success message and redirect to app details page
        model.addAttribute("success", "Review submitted successfully!");
        return "redirect:/user/apps/" + appId;
    }
}
