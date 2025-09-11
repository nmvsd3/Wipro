package com.playstore.ownerservice.client;

import com.playstore.ownerservice.dto.ReviewDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

// Feign client to communicate with the User Service
@FeignClient(name = "user-service", contextId = "reviewClient")
public interface ReviewClient {

    // Calls the User Service to get all reviews for a specific app
    @GetMapping("/api/reviews/{appId}")
    List<ReviewDTO> getReviews(@PathVariable("appId") Long appId);
}
