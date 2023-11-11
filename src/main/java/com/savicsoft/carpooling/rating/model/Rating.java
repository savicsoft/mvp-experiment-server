package com.savicsoft.carpooling.rating.model;

import com.savicsoft.carpooling.rating.enumeration.RatingType;
import com.savicsoft.carpooling.user.model.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.checkerframework.common.aliasing.qual.Unique;

import java.time.LocalDateTime;
import java.util.UUID;

/*
1st View →
List of Ratings(
    1-5 stars,
    comment,
    date,
    user/anonymous,
    type(passanger, driver))
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @NotNull(message = "User field must be filled appropriately")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull(message = "Rating must be added")
    private double rating;
    private String comment;
    private LocalDateTime date;
    private boolean anonymous;

    @NotNull(message = "Rating user field must be filled appropriately")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rating_user_id")
    private User ratingUser;

    @Enumerated(EnumType.STRING)
    private RatingType ratingType;
}
