package com.playstore.ownerservice.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "applications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 2000)
    private String description;

    private LocalDate releaseDate;
    private String version;
    private String genre;

    // This will be dynamically calculated if reviews exist
    private Double rating;

    private Long downloadCount;

    private boolean visible;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    // Not stored in DB, but useful when reviews are fetched from Review Service
    @Transient
    private List<Integer> reviewRatings;

    /**
     *  Dynamically compute the average rating from reviewRatings (if available).
     * If no reviews, fallback to stored rating or 0.0.
     */
    @Transient
    public Double getAverageRating() {
        if (reviewRatings != null && !reviewRatings.isEmpty()) {
            return reviewRatings.stream()
                    .mapToInt(Integer::intValue)
                    .average()
                    .orElse(0.0);
        }
        return rating != null ? rating : 0.0;
    }
}
