package com.playstore.ownerservice.entity;

import jakarta.persistence.*;
import lombok.*;

// JPA entity representing an application owner in the system
@Entity
@Table(name = "owners") // Maps to "owners" table in the database
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment primary key
    private Long id; // Unique owner ID

    private String name;     // Owner's full name
    private String email;    // Owner's email (used for login/notifications)
    private String phone;    // Owner's phone number
    private String password; // Hashed password for authentication
}
