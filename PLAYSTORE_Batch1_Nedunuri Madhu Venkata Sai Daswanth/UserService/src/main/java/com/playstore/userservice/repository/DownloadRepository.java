package com.playstore.userservice.repository;

import com.playstore.userservice.entity.Download;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DownloadRepository extends JpaRepository<Download, Long> {

    // Get all downloads by user
    List<Download> findByUserEmail(String userEmail);

    // check if user already downloaded a specific app
    Optional<Download> findByAppNameAndUserEmail(String appName, String userEmail);
}
