package com.playstore.ownerservice.repository;

import com.playstore.ownerservice.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Repository interface for Notification entity
// Provides CRUD operations and custom queries
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // Find notifications by owner ID and order them by creation time (latest first)
    List<Notification> findByOwnerIdOrderByCreatedAtDesc(Long ownerId);
}
