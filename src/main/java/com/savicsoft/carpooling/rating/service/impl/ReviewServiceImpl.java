package com.savicsoft.carpooling.rating.service.impl;

import com.savicsoft.carpooling.rating.model.dto.ReviewDTO;
import com.savicsoft.carpooling.rating.model.entity.Review;
import com.savicsoft.carpooling.rating.model.enumeration.ReviewType;
import com.savicsoft.carpooling.rating.model.mapper.CustomReviewMapper;
import com.savicsoft.carpooling.rating.repository.ReviewRepository;
import com.savicsoft.carpooling.rating.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.savicsoft.carpooling.rating.model.mapper.ReviewDTOMapper.INSTANCE;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final CustomReviewMapper customReviewMapper;
    @Override
    public List<ReviewDTO> findAll() {
        return INSTANCE.mapToReviewDTOList(reviewRepository.findAll())
                .stream().map(e -> {
                            if (e.isAnonymous())
                                e.setRaterUserId(null);
                            return e;
                        }
                ).collect(Collectors.toList());
    }

    @Override
    public List<ReviewDTO> findByRatedUserUuid(UUID ratedUserUuid) {
        return INSTANCE.mapToReviewDTOList(reviewRepository.findAllByRatedUser_Uuid(ratedUserUuid))
                .stream().map(e -> {
                    if (e.isAnonymous())
                        e.setRaterUserId(null);
                    return e;
                }).collect(Collectors.toList());
    }

    @Override
    public List<ReviewDTO> findByRaterUserUuid(UUID raterUserUuid) {
        return INSTANCE.mapToReviewDTOList(reviewRepository.findAllByRaterUser_Uuid(raterUserUuid))
                .stream().map(e -> {
                    if (e.isAnonymous())
                        e = null;
                        //e.setRaterUserId(null);
                    return e;
                }).collect(Collectors.toList());
    }

    @Override
    public Optional<ReviewDTO> findById(Long id) {
        ReviewDTO reviewDTO = INSTANCE.mapToReviewDTO(reviewRepository.findById(id).orElseThrow(
                () -> {
                    throw new RuntimeException("There was no rating found");
                }
        ));
        if (reviewDTO.isAnonymous()) {
            reviewDTO.setRaterUserId(null);
        }
        return Optional.of(reviewDTO);
    }

    @Override
    public ReviewDTO findByUuid(UUID uuid) {
        ReviewDTO reviewDTO = INSTANCE.mapToReviewDTO(reviewRepository.findByUuid(uuid).orElseThrow(
                () -> {
                    //TODO: custom exception
                    throw new RuntimeException("There was no rating found");
                }
        ));
        if (reviewDTO.isAnonymous()) {
            reviewDTO.setRaterUserId(null);
        }
        return reviewDTO;
    }

    @Override
    public List<ReviewDTO> findByRatedUserUuidAndRatingType(UUID ratedUserUuid, ReviewType reviewType) {
        return INSTANCE.mapToReviewDTOList(reviewRepository.findAllByRatedUser_UuidAndReviewType(ratedUserUuid, reviewType))
                .stream().map(e -> {
                    if (e.isAnonymous()) {
                        e.setRaterUserId(null);
                    }
                    return e;
                }).collect(Collectors.toList());
    }

    @Override
    public List<ReviewDTO> findByRaterUserUuidAndRatingType(UUID raterUserUuid, ReviewType reviewType) {
        return INSTANCE.mapToReviewDTOList(reviewRepository.findAllByRaterUser_UuidAndReviewType(raterUserUuid, reviewType))
                .stream().map(e -> {
                    if (e.isAnonymous()) {
                        e.setRaterUserId(null);
                    }
                    return e;
                }).collect(Collectors.toList());
    }

    @Override
    public ReviewDTO save(ReviewDTO reviewDTO) {
        Review reviewToSave = customReviewMapper.mapToReview(reviewDTO);
        reviewToSave.setUuid(UUID.randomUUID());
        Review review = reviewRepository.save(reviewToSave);
        return INSTANCE.mapToReviewDTO(review);
    }
}
