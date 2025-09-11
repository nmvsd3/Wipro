package com.playstore.ownerservice.controller;

import com.playstore.ownerservice.dto.ApplicationDTO;
import com.playstore.ownerservice.entity.Application;
import com.playstore.ownerservice.exception.AppNotFoundException;
import com.playstore.ownerservice.repository.ApplicationRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/apps")
public class ApplicationRestController {

    private final ApplicationRepository appRepository;

    public ApplicationRestController(ApplicationRepository appRepository) {
        this.appRepository = appRepository;
    }

    // Utility method to convert Entity -> DTO
    private ApplicationDTO toDTO(Application app) {
        return ApplicationDTO.builder()
                .id(app.getId())
                .name(app.getName())
                .description(app.getDescription())
                .releaseDate(app.getReleaseDate())
                .version(app.getVersion())
                .genre(app.getGenre())
                .rating(app.getRating() != null ? app.getRating() : 0.0)
                .downloadCount(app.getDownloadCount() != null ? app.getDownloadCount() : 0L)
                .visible(app.isVisible())
//                .imageUrl(app.getImageUrl())
                .build();
    }

    //  Get app by ID
    @GetMapping("/{id}")
    public ApplicationDTO getAppById(@PathVariable Long id) {
        Application app = appRepository.findById(id)
                .orElseThrow(() -> new AppNotFoundException("App not found with id " + id));
        return toDTO(app);
    }

    //  Search apps by name
    @GetMapping("/search")
    public List<ApplicationDTO> search(@RequestParam String name) {
        return appRepository.findAll().stream()
                .filter(app -> app.getName().toLowerCase().contains(name.toLowerCase()) && app.isVisible())
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    //  Filter apps by rating
    @GetMapping("/filter")
    public List<ApplicationDTO> filter(@RequestParam double rating) {
        return appRepository.findAll().stream()
                .filter(app -> app.getRating() != null && app.getRating() >= rating && app.isVisible())
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    //  Get apps by category
    @GetMapping("/category")
    public List<ApplicationDTO> getByCategory(@RequestParam String genre) {
        return appRepository.findAll().stream()
                .filter(app -> app.getGenre().equalsIgnoreCase(genre) && app.isVisible())
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Get all apps (useful for Browse All)
    @GetMapping("/all")
    public List<ApplicationDTO> getAllApps() {
        return appRepository.findAll().stream()
                .filter(Application::isVisible)
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
