package com.playstore.userservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

// Entity representing a user in the Playstore system
@Entity
@Table(name = "users") // Maps to "users" table in the database
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment primary key
    private Long id; // Unique user ID

    @NotBlank(message = "Check name") // Validation: must not be empty
    private String name;

    @Email(message = "Check email") // Validation: must be a valid email format
    @Column(unique = true, nullable = false) // DB constraint: unique + required
    private String email;

    @Pattern(regexp = "^[0-9]{10}$", message = "Check phone number") // Validation: must be 10 digits
    @Column(unique = true, nullable = false) // DB constraint: unique + required
    private String phone;

    @Size(min = 8, message = "Check password") // Validation: min 8 characters
    @Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
        message = "Check password" // Validation: must contain letters, digits, special chars
    )
    private String password; // Stored password (⚠should be hashed in production)
}
