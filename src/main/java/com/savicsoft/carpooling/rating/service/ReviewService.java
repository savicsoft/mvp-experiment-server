package com.savicsoft.carpooling.rating.service;

import com.savicsoft.carpooling.rating.model.dto.ReviewDTO;
import com.savicsoft.carpooling.rating.model.enumeration.ReviewType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewService {

    List<ReviewDTO> findAll();
    List<ReviewDTO> findByRatedUserUuid(UUID ratedUserUuid);
    List<ReviewDTO> findByRaterUserUuid(UUID raterUserUuid);
    Optional<ReviewDTO> findById(Long id);
    ReviewDTO findByUuid(UUID uuid);
    List<ReviewDTO> findByRatedUserUuidAndRatingType(UUID ratedUserUuid, ReviewType reviewType);
    List<ReviewDTO> findByRaterUserUuidAndRatingType(UUID raterUserUuid, ReviewType reviewType);
    ReviewDTO save(ReviewDTO reviewDTO);
}
