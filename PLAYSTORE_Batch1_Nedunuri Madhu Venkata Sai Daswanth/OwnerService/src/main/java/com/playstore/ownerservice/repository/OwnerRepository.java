package com.playstore.ownerservice.repository;

import com.playstore.ownerservice.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Repository interface for Owner entity
// Provides CRUD operations and custom query methods
public interface OwnerRepository extends JpaRepository<Owner, Long> {

    // Find an owner by email or phone (used for login/authentication checks)
    Optional<Owner> findByEmailOrPhone(String email, String phone);
}
