package com.playstore.userservice.service;

import com.playstore.userservice.entity.User;
import com.playstore.userservice.exception.InvalidCredentialsException;
import com.playstore.userservice.exception.UserAlreadyExistsException;
import com.playstore.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Service layer for managing user registration, login, and queries
@Service
public class UserService {

    private final UserRepository repo;

    // Constructor injection of repository
    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    // Register a new user (throws exception if email or phone already exists)
    public User register(User user) {
        Optional<User> existing = repo.findByEmailOrPhone(user.getEmail(), user.getPhone());
        if (existing.isPresent()) throw new UserAlreadyExistsException("User already exists");
        return repo.save(user);
    }

    // Get list of all user emails (used by other services for notifications)
    public List<String> getAllUserEmails() {
        return repo.findAll()
                   .stream()
                   .map(User::getEmail)
                   .toList();
    }

    // Get list of all users
    public List<User> getAllUsers() {
        return repo.findAll();
    }

    // Login using email or phone + password (throws exception if invalid)
    public User login(String emailOrPhone, String password) {
        Optional<User> user = repo.findByEmailOrPhone(emailOrPhone, emailOrPhone);
        if (user.isEmpty() || !user.get().getPassword().equals(password)) {
            throw new InvalidCredentialsException("Invalid credentials");
        }
        return user.get();
    }
}
