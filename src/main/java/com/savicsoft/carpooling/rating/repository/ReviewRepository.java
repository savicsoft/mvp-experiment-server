package com.savicsoft.carpooling.rating.repository;

import com.savicsoft.carpooling.rating.model.entity.Review;
import com.savicsoft.carpooling.rating.model.enumeration.ReviewType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByRatedUser_Uuid(UUID userUuid);
    List<Review> findAllByRaterUser_Uuid(UUID userUuid);
    Optional<Review> findByUuid(UUID id);
    List<Review> findAllByRatedUser_UuidAndReviewType(UUID userUuid, ReviewType reviewType);
    List<Review> findAllByRaterUser_UuidAndReviewType(UUID userUuid, ReviewType reviewType);
}
