package com.savicsoft.carpooling.rating.repository;

import com.savicsoft.carpooling.rating.models.entity.Review;
import com.savicsoft.carpooling.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<List<Review>> findAllByUserId(Long id);
    Optional<Review> findByUuid(UUID uuid);
}
