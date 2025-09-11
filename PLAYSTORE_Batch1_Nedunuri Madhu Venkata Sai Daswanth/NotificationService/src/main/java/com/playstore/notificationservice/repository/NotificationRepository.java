package com.playstore.notificationservice.repository;

import com.playstore.notificationservice.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Repository interface for Notification entity
// Provides CRUD operations and custom queries
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // Custom query method to find notifications by recipient (email/user)
    List<Notification> findByRecipient(String recipient);
}
