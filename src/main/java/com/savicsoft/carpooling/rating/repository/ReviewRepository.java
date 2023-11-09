package com.savicsoft.carpooling.rating.repository;

import com.savicsoft.carpooling.rating.models.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
