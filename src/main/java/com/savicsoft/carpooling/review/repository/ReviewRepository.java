package com.savicsoft.carpooling.review.repository;

import com.savicsoft.carpooling.review.model.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByRatedUser_Id(UUID userId);
    List<Review> findAllByRaterUser_Email(String email);

    Optional<Review> findById(UUID id);
    Long deleteById(UUID id);
}
