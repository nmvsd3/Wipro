package com.playstore.userservice.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.playstore.userservice.dto.ApplicationDTO;
import com.playstore.userservice.entity.Download;
import com.playstore.userservice.entity.Review;
import com.playstore.userservice.entity.User;
import com.playstore.userservice.service.ReviewService;
import com.playstore.userservice.service.UserAppService;

// Controller for handling user interactions with applications (UI pages)
@Controller
@RequestMapping("/user/apps")
public class UserAppController {

    private final UserAppService appService;
    private final ReviewService reviewService;

    // Constructor injection
    public UserAppController(UserAppService appService, ReviewService reviewService) {
        this.appService = appService;
        this.reviewService = reviewService;
    }

    // Show all apps available to the logged-in user
    @GetMapping
    public String listAllApps(@SessionAttribute("user") User user, Model model) {
        List<ApplicationDTO> apps = appService.getAllApps();
        if (apps == null || apps.isEmpty()) {
            model.addAttribute("message", "No apps available at the moment.");
        } else {
            // ✅ mark downloaded & update status
            apps.forEach(app -> {
                boolean downloaded = appService.isAppDownloaded(app.getName(), user.getEmail());
                app.setDownloaded(downloaded);

                if (downloaded) {
                    String installedVersion = appService.getUserInstalledVersion(app.getName(), user.getEmail());
                    if (installedVersion != null && !installedVersion.equals(app.getVersion())) {
                        app.setUpdateAvailable(true); // ✅ mark update
                    }
                }
            });
            model.addAttribute("apps", apps);
        }
        model.addAttribute("user", user);
        return "user-apps"; // returns Thymeleaf template
    }

    // Search apps by name
    @GetMapping("/search")
    public String search(@RequestParam(required = false) String name,
                         @SessionAttribute("user") User user,
                         Model model) {
        List<ApplicationDTO> apps = (name == null || name.isBlank())
                ? appService.getAllApps()
                : appService.searchApps(name);

        // ✅ mark downloaded & update status
        apps.forEach(app -> {
            boolean downloaded = appService.isAppDownloaded(app.getName(), user.getEmail());
            app.setDownloaded(downloaded);

            if (downloaded) {
                String installedVersion = appService.getUserInstalledVersion(app.getName(), user.getEmail());
                if (installedVersion != null && !installedVersion.equals(app.getVersion())) {
                    app.setUpdateAvailable(true);
                }
            }
        });

        model.addAttribute("apps", apps);
        model.addAttribute("user", user);
        return "user-apps";
    }

    // Filter apps by category/genre
    @GetMapping("/category")
    public String category(@RequestParam String genre,
                           @SessionAttribute("user") User user,
                           Model model) {
        List<ApplicationDTO> apps = appService.getByCategory(genre);

        // ✅ mark downloaded & update status
        apps.forEach(app -> {
            boolean downloaded = appService.isAppDownloaded(app.getName(), user.getEmail());
            app.setDownloaded(downloaded);

            if (downloaded) {
                String installedVersion = appService.getUserInstalledVersion(app.getName(), user.getEmail());
                if (installedVersion != null && !installedVersion.equals(app.getVersion())) {
                    app.setUpdateAvailable(true);
                }
            }
        });

        model.addAttribute("apps", apps);
        model.addAttribute("user", user);
        return "user-apps";
    }

    // Filter apps by rating
    @GetMapping("/filter")
    public String filter(@RequestParam double rating,
                         @SessionAttribute("user") User user,
                         Model model) {
        List<ApplicationDTO> apps = appService.filterApps(rating);

        // ✅ mark downloaded & update status
        apps.forEach(app -> {
            boolean downloaded = appService.isAppDownloaded(app.getName(), user.getEmail());
            app.setDownloaded(downloaded);

            if (downloaded) {
                String installedVersion = appService.getUserInstalledVersion(app.getName(), user.getEmail());
                if (installedVersion != null && !installedVersion.equals(app.getVersion())) {
                    app.setUpdateAvailable(true);
                }
            }
        });

        model.addAttribute("apps", apps);
        model.addAttribute("user", user);
        return "user-apps";
    }

    // View app details page
    @GetMapping("/{id}")
    public String viewApp(@PathVariable Long id,
                          @SessionAttribute("user") User user,
                          Model model) {
        try {
            ApplicationDTO app = appService.getAppById(id);
            boolean isDownloaded = appService.isAppDownloaded(app.getName(), user.getEmail());
            app.setDownloaded(isDownloaded);

            // ✅ check update availability
            if (isDownloaded) {
                String installedVersion = appService.getUserInstalledVersion(app.getName(), user.getEmail());
                if (installedVersion != null && !installedVersion.equals(app.getVersion())) {
                    app.setUpdateAvailable(true);
                }
            }

            model.addAttribute("app", app);
            model.addAttribute("reviews", reviewService.getReviewsByApp(id));
            model.addAttribute("isDownloaded", isDownloaded);
            model.addAttribute("user", user);

            return "app-details"; // shows single app page
        } catch (Exception e) {
            model.addAttribute("error", "App not found.");
            return "user-apps";
        }
    }

    // Add review for an app (from details page form)
    @PostMapping("/{id}/review")
    public String addReview(@PathVariable Long id,
                            @RequestParam String userName,
                            @RequestParam String comment,
                            @RequestParam int rating) {
        Review review = Review.builder()
                .appId(id)
                .userName(userName)
                .comment(comment)
                .rating(rating)
                .build();
        reviewService.addReview(review);
        return "redirect:/user/apps/" + id; // redirect back to details page
    }

    // Show downloads of the logged-in user
    @GetMapping("/downloads")
    public String myDownloads(@SessionAttribute("user") User user, Model model) {
        List<Download> downloads = appService.getUserDownloads(user.getEmail());

        model.addAttribute("downloads", downloads);
        model.addAttribute("user", user);

        return "user-downloads";
    }

    // Download or Update an app
    @PostMapping("/{id}/download")
    public String downloadApp(@PathVariable Long id,
                              @SessionAttribute("user") User user,
                              RedirectAttributes redirectAttributes) {
        ApplicationDTO app = appService.getAppById(id);
        try {
            String result = appService.downloadApp(app.getName(), user.getEmail());
            redirectAttributes.addFlashAttribute("success", result);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Download failed: " + e.getMessage());
        }
        return "redirect:/user/apps/" + id;
    }

    // Uninstall an app
    @PostMapping("/{id}/uninstall")
    public String uninstallApp(@PathVariable Long id,
                               @SessionAttribute("user") User user,
                               RedirectAttributes redirectAttributes) {
        ApplicationDTO app = appService.getAppById(id);
        try {
            String result = appService.uninstallApp(app.getName(), user.getEmail());
            redirectAttributes.addFlashAttribute("success", result);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Uninstall failed: " + e.getMessage());
        }
        // redirect to downloads page so user sees the updated list
        return "redirect:/user/apps/downloads";
    }
}
