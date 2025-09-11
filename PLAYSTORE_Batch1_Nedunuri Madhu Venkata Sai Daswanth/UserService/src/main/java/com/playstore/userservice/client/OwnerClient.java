package com.playstore.userservice.client;

import com.playstore.userservice.dto.ApplicationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

// Feign client for communicating with the Owner Service
@FeignClient(name = "owner-service", url = "http://localhost:8081")
public interface OwnerClient {

    // Get all applications (admin/internal use)
    @GetMapping("/api/apps/all")
    List<ApplicationDTO> getAllApps();

    // Search applications by name
    @GetMapping("/api/apps/search")
    List<ApplicationDTO> searchApps(@RequestParam String name);

    // Filter applications by rating
    @GetMapping("/api/apps/filter")
    List<ApplicationDTO> filterApps(@RequestParam double rating);

    // Get applications by category/genre
    @GetMapping("/api/apps/category")
    List<ApplicationDTO> getByCategory(@RequestParam String genre);

    // Get details of a specific application by ID
    @GetMapping("/api/apps/{id}")
    ApplicationDTO getAppById(@PathVariable Long id);

    // Download an app (increments download count in Owner Service)
    @PostMapping("/owner/apps/download")
    Map<String, Object> downloadApp(@RequestParam String appName,
                                    @RequestParam String userEmail);

    // Uninstall an app (decrements download count in Owner Service)
    @PostMapping("/owner/apps/uninstall")
    Map<String, Object> uninstallApp(@RequestParam String appName,
                                     @RequestParam String userEmail);
}
