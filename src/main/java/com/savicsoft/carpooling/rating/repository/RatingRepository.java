package com.savicsoft.carpooling.rating.repository;

import com.savicsoft.carpooling.rating.enumeration.RatingType;
import com.savicsoft.carpooling.rating.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

        List<Rating> findAll();
        List<Rating> findByUser_Uuid(UUID userUuid);
        List<Rating> findByRatingUser_Uuid(UUID ratingUserUuid);
        Optional<Rating> findById(Long id);
        Optional<Rating> findByUuid(UUID uuid);
        List<Rating> findByUser_UuidAndRatingType(UUID userUuid, RatingType ratingType);
        List<Rating> findByRatingUser_UuidAndRatingType(UUID ratingUserUuid,RatingType ratingType);




}
