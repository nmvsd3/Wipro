package com.playstore.userservice.service;

import com.playstore.userservice.client.OwnerClient;
import com.playstore.userservice.dto.ApplicationDTO;
import com.playstore.userservice.entity.Download;
import com.playstore.userservice.repository.DownloadRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// Service layer for managing user interactions with applications
@Service
public class UserAppService {

    private final OwnerClient ownerClient;         // Feign client to communicate with Owner Service
    private final DownloadRepository downloadRepo; // Repository for storing local download records

    // Constructor injection
    public UserAppService(OwnerClient ownerClient, DownloadRepository downloadRepo) {
        this.ownerClient = ownerClient;
        this.downloadRepo = downloadRepo;
    }

    // Get all applications (from Owner Service)
    public List<ApplicationDTO> getAllApps() {
        return ownerClient.getAllApps();
    }

    // Search applications by name (from Owner Service)
    public List<ApplicationDTO> searchApps(String name) {
        return ownerClient.searchApps(name);
    }

    // Filter applications by rating (from Owner Service)
    public List<ApplicationDTO> filterApps(double rating) {
        return ownerClient.filterApps(rating);
    }

    // Get applications by category/genre (from Owner Service)
    public List<ApplicationDTO> getByCategory(String genre) {
        return ownerClient.getByCategory(genre);
    }

    // Get details of a specific application (from Owner Service)
    public ApplicationDTO getAppById(Long id) {
        return ownerClient.getAppById(id);
    }

    // Download or update an app → notify Owner Service + record locally
    public String downloadApp(String appName, String userEmail) {
        // Check if already downloaded
        Optional<Download> existing = downloadRepo.findByAppNameAndUserEmail(appName, userEmail);

        ApplicationDTO app = ownerClient.searchApps(appName).stream()
                .filter(a -> a.getName().equalsIgnoreCase(appName))
                .findFirst()
                .orElse(null);

        if (app == null) {
            return "App not found.";
        }

        if (existing.isPresent()) {
            Download download = existing.get();

            //  If version is same → already up to date
            if (download.getVersion() != null && download.getVersion().equals(app.getVersion())) {
                return "App is already up to date.";
            }

            // If version is different → update local record, no Owner Service call
            download.setVersion(app.getVersion());
            download.setDownloadedAt(LocalDateTime.now());
            downloadRepo.save(download);

            return "App updated to latest version.";
        } else {
            //  First time download → call Owner Service & save locally
            Map<String, Object> response = ownerClient.downloadApp(appName, userEmail);

            if ("success".equals(response.get("status"))) {
                Download download = Download.builder()
                        .appName(appName)
                        .userEmail(userEmail)
                        .version(app.getVersion())
                        .downloadedAt(LocalDateTime.now())
                        .build();
                downloadRepo.save(download);

                Object msg = response.get("message");
                return msg != null ? msg.toString() : "Downloaded successfully.";
            } else {
                Object msg = response.get("message");
                return "Download failed: " + (msg != null ? msg.toString() : "unknown");
            }
        }
    }


    // Get all downloads for a specific user
    public List<Download> getUserDownloads(String userEmail) {
        return downloadRepo.findByUserEmail(userEmail);
    }

    // Check if a user has already downloaded a specific app
    public boolean isAppDownloaded(String appName, String userEmail) {
        return downloadRepo.findByAppNameAndUserEmail(appName, userEmail).isPresent();
    }

    //  Get the installed version of an app for a specific user
    public String getUserInstalledVersion(String appName, String userEmail) {
        return downloadRepo.findByAppNameAndUserEmail(appName, userEmail)
                .map(Download::getVersion)
                .orElse(null);
    }

    // Uninstall an app → notify Owner Service + remove from local DB
    public String uninstallApp(String appName, String userEmail) {
        // Step 1: Notify Owner Service so owner’s dashboard can update counts
        Map<String, Object> ownerResp = ownerClient.uninstallApp(appName, userEmail);

        if (!"success".equals(ownerResp.get("status"))) {
            Object msg = ownerResp.get("message");
            return "Failed to uninstall on owner service: " + (msg != null ? msg.toString() : "unknown");
        }

        // Step 2: Remove the download record locally
        return downloadRepo.findByAppNameAndUserEmail(appName, userEmail)
                .map(download -> {
                    downloadRepo.delete(download);
                    return "App uninstalled successfully.";
                })
                .orElse("App not found in your downloads.");
    }
}
