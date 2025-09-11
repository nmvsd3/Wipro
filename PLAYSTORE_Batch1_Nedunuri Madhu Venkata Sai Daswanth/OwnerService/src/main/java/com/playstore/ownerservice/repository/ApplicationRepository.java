package com.playstore.ownerservice.repository;

import com.playstore.ownerservice.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// Repository interface for Application entity
// Provides built-in CRUD operations and custom query methods
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    // Find an application by its exact name
    Optional<Application> findByName(String name);

    // Find applications by genre (case-insensitive)
    List<Application> findByGenreIgnoreCase(String genre);

    // Search applications by name containing a keyword (case-insensitive)
    List<Application> findByNameContainingIgnoreCase(String name);
}
