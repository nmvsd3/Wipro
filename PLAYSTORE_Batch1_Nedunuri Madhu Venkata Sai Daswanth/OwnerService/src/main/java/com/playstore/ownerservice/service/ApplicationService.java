package com.playstore.ownerservice.service;

import com.playstore.ownerservice.client.NotificationClient;
import com.playstore.ownerservice.client.ReviewClient;
import com.playstore.ownerservice.client.UserClient;
import com.playstore.ownerservice.dto.ReviewDTO;
import com.playstore.ownerservice.entity.Application;
import com.playstore.ownerservice.entity.Notification;
import com.playstore.ownerservice.entity.Owner;
import com.playstore.ownerservice.exception.AppNotFoundException;
import com.playstore.ownerservice.repository.ApplicationRepository;
import com.playstore.ownerservice.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

// Service layer for managing applications, reviews, and notifications
@Service
public class ApplicationService {

    private final ApplicationRepository repo;
    private final NotificationRepository notificationRepo;
    private final NotificationClient notificationClient;
    private final UserClient userClient;
    private final ReviewClient reviewClient;

    // Constructor injection for dependencies
    public ApplicationService(ApplicationRepository repo,
                              NotificationRepository notificationRepo,
                              NotificationClient notificationClient,
                              UserClient userClient,
                              ReviewClient reviewClient) {
        this.repo = repo;
        this.notificationRepo = notificationRepo;
        this.notificationClient = notificationClient;
        this.userClient = userClient;
        this.reviewClient = reviewClient;
    }

    // Get all apps (admin/internal) with reviews & ratings attached
    public List<Application> getAllApps() {
        return repo.findAll().stream()
                .map(this::attachReviewsAndRating)
                .toList();
    }

    // Get only visible apps (for browsing)
    public List<Application> getAllVisibleApps() {
        return repo.findAll().stream()
                .filter(Application::isVisible)
                .map(this::attachReviewsAndRating)
                .toList();
    }

    // Find app by name
    public Application findByName(String name) {
        Application app = repo.findByName(name)
                .orElseThrow(() ->
                        new AppNotFoundException("Application with name '" + name + "' not found"));
        return attachReviewsAndRating(app);
    }

    // Get app by ID
    public Application getById(Long id) {
        Application app = repo.findById(id)
                .orElseThrow(() ->
                        new AppNotFoundException("App not found with id " + id));
        return attachReviewsAndRating(app);
    }

    // Search visible apps by name (case-insensitive)
    public List<Application> searchByName(String name) {
        return repo.findAll().stream()
                .filter(app -> app.getName().toLowerCase().contains(name.toLowerCase())
                        && app.isVisible())
                .map(this::attachReviewsAndRating)
                .toList();
    }

    // Filter apps by minimum rating
    public List<Application> filterByRating(double rating) {
        return repo.findAll().stream()
                .map(this::attachReviewsAndRating)
                .filter(app -> app.getAverageRating() >= rating && app.isVisible())
                .toList();
    }

    // Find apps by genre/category
    public List<Application> findByGenre(String genre) {
        return repo.findAll().stream()
                .filter(app -> app.getGenre().equalsIgnoreCase(genre)
                        && app.isVisible())
                .map(this::attachReviewsAndRating)
                .toList();
    }

    // Save a new app and notify all users & owner
    public Application save(Application app) {
        if (app.getDownloadCount() == null) app.setDownloadCount(0L);

        Application saved = repo.save(app);

        // Notify all users about new app
        List<String> users = userClient.getAllUserEmails();
        if (users != null && !users.isEmpty()) {
            for (String email : users) {
                try {
                    notificationClient.sendNotification(
                            email,
                            "New App Uploaded: " + saved.getName(),
                            "Check out " + saved.getName() + " in category " + saved.getGenre()
                    );
                } catch (Exception e) {
                    System.out.println("Failed to send notification to " + email + ": " + e.getMessage());
                }
            }
        }

        // Notify owner (dashboard notification)
        if (saved.getOwner() != null) {
            notificationRepo.save(Notification.builder()
                    .owner(saved.getOwner())
                    .message("You uploaded a new app: " + saved.getName())
                    .createdAt(LocalDateTime.now())
                    .build());
        }

        return saved;
    }

    // Update an existing app and notify users & owner
    public Application update(Long id, Application updated) {
        Application app = getById(id);

        app.setName(updated.getName());
        app.setDescription(updated.getDescription());
        app.setReleaseDate(updated.getReleaseDate());
        app.setVersion(updated.getVersion());
        app.setGenre(updated.getGenre());
        app.setVisible(updated.isVisible());

        Application saved = repo.save(app);

        // Notify owner
        if (saved.getOwner() != null) {
            notificationRepo.save(Notification.builder()
                    .owner(saved.getOwner())
                    .message("You updated app: " + saved.getName())
                    .createdAt(LocalDateTime.now())
                    .build());
        }

        // Notify all users about update
        List<String> users = userClient.getAllUserEmails();
        if (users != null && !users.isEmpty()) {
            for (String email : users) {
                try {
                    notificationClient.sendNotification(
                            email,
                            "App Updated: " + saved.getName(),
                            "The app " + saved.getName() + " has a new update. Check it out!"
                    );
                } catch (Exception e) {
                    System.out.println("Failed to notify " + email + ": " + e.getMessage());
                }
            }
        }

        return attachReviewsAndRating(saved);
    }

    // Delete an app by ID
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new AppNotFoundException("App not found with id " + id);
        }
        repo.deleteById(id);
    }

    // Get all reviews for a given app
    public List<ReviewDTO> getReviewsForApp(Long appId) {
        return reviewClient.getReviews(appId);
    }

    // Get total downloads across all apps by a specific owner
    public long getTotalDownloadsByOwner(Long ownerId) {
        return repo.findAll().stream()
                .filter(app -> app.getOwner() != null
                        && app.getOwner().getId().equals(ownerId))
                .mapToLong(a -> a.getDownloadCount() != null ? a.getDownloadCount() : 0)
                .sum();
    }

    // Get all apps by a specific owner
    public List<Application> getAppsByOwner(Long ownerId) {
        return repo.findAll().stream()
                .filter(app -> app.getOwner() != null
                        && app.getOwner().getId().equals(ownerId))
                .map(this::attachReviewsAndRating)
                .toList();
    }

    // Increment app downloads when a user installs it, notify owner
    public Application incrementDownloads(Application app, String userEmail) {
        if (app.getDownloadCount() == null) app.setDownloadCount(0L);
        app.setDownloadCount(app.getDownloadCount() + 1);
        Application updated = repo.save(app);

        Owner owner = updated.getOwner();
        if (owner != null && owner.getEmail() != null && !owner.getEmail().isBlank()) {
            String msg = "User " + userEmail + " downloaded your app " + updated.getName();

            try {
                notificationClient.sendNotification(
                        owner.getEmail(),
                        "Your app was downloaded!",
                        msg + ". Total downloads: " + updated.getDownloadCount()
                );
            } catch (Exception e) {
                // log error but continue
            }

            notificationRepo.save(Notification.builder()
                    .owner(owner)
                    .message(msg)
                    .createdAt(LocalDateTime.now())
                    .build());
        }
        return attachReviewsAndRating(updated);
    }

    // Decrement app downloads when a user uninstalls it, notify owner
    public Application decrementDownloads(Application app, String userEmail) {
        if (app.getDownloadCount() == null || app.getDownloadCount() <= 0) {
            app.setDownloadCount(0L);
        } else {
            app.setDownloadCount(app.getDownloadCount() - 1);
        }
        Application updated = repo.save(app);

        Owner owner = updated.getOwner();
        if (owner != null && owner.getEmail() != null && !owner.getEmail().isBlank()) {
            String msg = "User " + userEmail + " uninstalled your app " + updated.getName();

            try {
                notificationClient.sendNotification(
                        owner.getEmail(),
                        "An uninstall occurred",
                        msg + ". Total downloads: " + updated.getDownloadCount()
                );
            } catch (Exception e) {
                System.out.println("Failed to notify owner about uninstall: " + e.getMessage());
            }

            notificationRepo.save(Notification.builder()
                    .owner(owner)
                    .message(msg)
                    .createdAt(LocalDateTime.now())
                    .build());
        }
        return attachReviewsAndRating(updated);
    }

    // Announce an app update to all users and notify owner
    public void announceUpdate(Long appId, String updateMessage) {
        Application app = getById(appId);

        // Save owner notification
        if (app.getOwner() != null) {
            notificationRepo.save(Notification.builder()
                    .owner(app.getOwner())
                    .message("You announced update for " + app.getName())
                    .createdAt(LocalDateTime.now())
                    .build());
        }

        // Notify all users by email
        List<String> emails = userClient.getAllUserEmails();
        if (emails != null && !emails.isEmpty()) {
            for (String email : emails) {
                try {
                    notificationClient.sendNotification(
                            email,
                            "Update available for " + app.getName(),
                            updateMessage
                    );
                } catch (Exception e) {
                    // log error but continue
                }
            }
        }
    }

    // Helper: attach reviews to app and compute average rating dynamically
    private Application attachReviewsAndRating(Application app) {
        try {
            List<ReviewDTO> reviews = reviewClient.getReviews(app.getId());
            if (reviews != null && !reviews.isEmpty()) {
                app.setReviewRatings(
                        reviews.stream()
                                .map(ReviewDTO::getRating)
                                .collect(Collectors.toList())
                );
                app.setRating(app.getAverageRating()); // update with computed avg rating
            }
        } catch (Exception e) {
            System.out.println("Failed to fetch reviews for app " + app.getId() + ": " + e.getMessage());
        }
        return app;
    }
}
