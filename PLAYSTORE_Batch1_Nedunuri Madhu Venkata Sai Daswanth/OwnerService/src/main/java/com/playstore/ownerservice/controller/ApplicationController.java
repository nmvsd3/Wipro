package com.playstore.ownerservice.controller;

import com.playstore.ownerservice.entity.Application;
import com.playstore.ownerservice.exception.AppNotFoundException;
import com.playstore.ownerservice.service.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/owner/apps")
public class ApplicationController {

    private final ApplicationService appService;

    public ApplicationController(ApplicationService appService) {
        this.appService = appService;
    }

    // List all apps
    @GetMapping
    public String listApps(Model model) {
        model.addAttribute("apps", appService.getAllApps());
        return "apps"; // apps.html
    }

    // Show add form
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("app", new Application());
        return "add-app"; // add-app.html
    }

    //  Handle add form submit 
    @PostMapping("/add")
    public String addApp(@ModelAttribute("app") @Valid Application app,
                         @SessionAttribute("owner") com.playstore.ownerservice.entity.Owner owner,
                         RedirectAttributes redirectAttributes) {
        try {
            app.setOwner(owner);
            appService.save(app);
            redirectAttributes.addFlashAttribute("success", "App added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to add app: " + e.getMessage());
        }
        return "redirect:/owner/dashboard";
    }

    // Edit app
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("app", appService.getById(id));
            return "edit-app"; // edit-app.html
        } catch (AppNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
            return "apps"; // fallback to apps.html
        }
    }

    @PostMapping("/edit/{id}")
    public String editApp(@PathVariable Long id,
                          @ModelAttribute("app") Application app,
                          RedirectAttributes redirectAttributes) {
        try {
            appService.update(id, app);
            redirectAttributes.addFlashAttribute("success", "App updated successfully!");
        } catch (AppNotFoundException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/owner/apps";
    }

    //  Delete app
    @GetMapping("/delete/{id}")
    public String deleteApp(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            appService.delete(id);
            redirectAttributes.addFlashAttribute("success", "App deleted successfully!");
        } catch (AppNotFoundException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/owner/apps";
    }

    //  View reviews for an app
    @GetMapping("/{id}/reviews")
    public String viewReviews(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("app", appService.getById(id));
            var reviews = appService.getReviewsForApp(id);
            if (reviews == null || reviews.isEmpty()) {
                model.addAttribute("message", "No reviews yet for this app.");
            } else {
                model.addAttribute("reviews", reviews);
            }
            return "reviews"; // reviews.html
        } catch (AppNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
            return "apps"; // fallback
        }
    }

    //  Send announcement to app users
    @PostMapping("/{id}/announce")
    public String announce(@PathVariable Long id,
                           @RequestParam String message,
                           RedirectAttributes redirectAttributes) {
        appService.announceUpdate(id, message);
        redirectAttributes.addFlashAttribute("success", "Announcement sent to all users!");
        return "redirect:/owner/dashboard";
    }

    //  Show upload form
    @GetMapping("/upload")
    public String showUploadForm(Model model) {
        model.addAttribute("app", new Application());
        return "add-app"; // must match add-app.html in templates
    }

    //  Download app (for user-service calls)
    @PostMapping("/download")
    @ResponseBody
    public Map<String, Object> downloadApp(@RequestParam String appName,
                                           @RequestParam String userEmail) {
        Map<String, Object> response = new HashMap<>();
        try {
            Application app = appService.findByName(appName);
            Application updated = appService.incrementDownloads(app, userEmail);

            response.put("status", "success");
            response.put("message", "App '" + appName + "' downloaded successfully by " + userEmail);
            response.put("totalDownloads", updated.getDownloadCount());
        } catch (AppNotFoundException ex) {
            response.put("status", "error");
            response.put("message", ex.getMessage());
        } catch (Exception ex) {
            response.put("status", "error");
            response.put("message", "Unexpected error: " + ex.getMessage());
        }
        return response;
    }

    //  Uninstall app (called by user-service when a user uninstalls)
    @PostMapping("/uninstall")
    @ResponseBody
    public Map<String, Object> uninstallApp(@RequestParam String appName,
                                            @RequestParam String userEmail) {
        Map<String, Object> response = new HashMap<>();
        try {
            Application app = appService.findByName(appName);
            Application updated = appService.decrementDownloads(app, userEmail);

            response.put("status", "success");
            response.put("message", "User '" + userEmail + "' uninstalled app '" + appName + "'.");
            response.put("totalDownloads", updated.getDownloadCount());
        } catch (AppNotFoundException ex) {
            response.put("status", "error");
            response.put("message", ex.getMessage());
        } catch (Exception ex) {
            response.put("status", "error");
            response.put("message", "Unexpected error: " + ex.getMessage());
        }
        return response;
    }
}
