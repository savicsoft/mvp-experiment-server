package com.savicsoft.carpooling.rating.service;

import com.savicsoft.carpooling.rating.dto.RatingDTO;
import com.savicsoft.carpooling.rating.enumeration.RatingType;
import com.savicsoft.carpooling.rating.model.Rating;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RatingService {

    List<RatingDTO> findAll();
    List<RatingDTO> findByUser_Uuid(UUID userUuid);
    List<RatingDTO> findByRatingUser_Uuid(UUID ratingUserUuid);
    Optional<RatingDTO> findById(Long id);
    Optional<RatingDTO> findByUuid(UUID uuid);
    List<RatingDTO> findByUser_UuidAndRatingType(UUID userUuid, RatingType ratingType);
    List<RatingDTO> findByRatingUser_UuidAndRatingType(UUID ratingUserUuid,RatingType ratingType);

}
