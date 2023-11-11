package com.savicsoft.carpooling.rating.repository;

import com.savicsoft.carpooling.rating.models.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<List<Review>> findAllByUserId(Long id);

}
