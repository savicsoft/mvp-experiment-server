package com.savicsoft.carpooling.review.model.entity;

import com.savicsoft.carpooling.review.model.enumeration.ReviewType;
import com.savicsoft.carpooling.user.model.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

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
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rated_user_id")
    private User ratedUser;

    private double rating;
    private String comment;
    private LocalDateTime date;
    private boolean anonymous;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rater_user_id")
    private User raterUser;

    // rated user is a ...
    @Enumerated(EnumType.STRING)
    private ReviewType reviewType;
}
