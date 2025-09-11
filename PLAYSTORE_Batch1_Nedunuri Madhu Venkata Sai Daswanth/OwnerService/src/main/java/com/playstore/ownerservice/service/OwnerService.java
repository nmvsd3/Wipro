package com.playstore.ownerservice.service;

import com.playstore.ownerservice.entity.Owner;
import com.playstore.ownerservice.exception.InvalidCredentialsException;
import com.playstore.ownerservice.exception.OwnerAlreadyExistsException;
import com.playstore.ownerservice.repository.OwnerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

// Service layer for managing Owner registration and login
@Service
public class OwnerService {

    private final OwnerRepository repo;

    // Constructor injection of OwnerRepository
    public OwnerService(OwnerRepository repo) {
        this.repo = repo;
    }

    // Register a new owner (throws exception if email/phone already exists)
    public Owner register(Owner owner) {
        Optional<Owner> existing = repo.findByEmailOrPhone(owner.getEmail(), owner.getPhone());
        if (existing.isPresent()) throw new OwnerAlreadyExistsException("Owner already exists");
        return repo.save(owner);
    }

    // Login using email or phone and password
    public Owner login(String emailOrPhone, String password) {
        Optional<Owner> owner = repo.findByEmailOrPhone(emailOrPhone, emailOrPhone);
        if (owner.isEmpty() || !owner.get().getPassword().equals(password)) {
            throw new InvalidCredentialsException("Invalid credentials");
        }
        return owner.get();
    }
}
