package com.playstore.userservice.repository;

import com.playstore.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Repository for User entity
// Provides CRUD operations and custom query methods
public interface UserRepository extends JpaRepository<User, Long> {

    // Find a user by email or phone (used for login and registration checks)
    Optional<User> findByEmailOrPhone(String email, String phone);
}
