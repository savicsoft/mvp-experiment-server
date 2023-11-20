package com.savicsoft.carpooling.review.service.impl;

import com.savicsoft.carpooling.exception.CouldNotCreateException;
import com.savicsoft.carpooling.exception.CouldNotDeleteException;
import com.savicsoft.carpooling.exception.CouldNotUpdateException;
import com.savicsoft.carpooling.exception.NotFoundException;
import com.savicsoft.carpooling.review.model.dto.ReviewDTO;
import com.savicsoft.carpooling.review.model.entity.Review;
import com.savicsoft.carpooling.review.model.form.CreateReviewForm;
import com.savicsoft.carpooling.review.model.form.UpdateReviewForm;
import com.savicsoft.carpooling.review.model.mapper.CustomReviewMapper;
import com.savicsoft.carpooling.review.repository.ReviewRepository;
import com.savicsoft.carpooling.review.service.ReviewService;
import com.savicsoft.carpooling.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.savicsoft.carpooling.review.model.mapper.ReviewDTOMapper.INSTANCE;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final CustomReviewMapper customReviewMapper;

    @Override
    public List<ReviewDTO> findByRatedUserId(UUID ratedUserId) {
        //will rewrite to existsById from userService
        if(!userRepository.existsById(ratedUserId)){
            throw new NotFoundException("User not found by id: " + ratedUserId);
        }
        return INSTANCE.mapToReviewDTOList(reviewRepository.findAllByRatedUser_Id(ratedUserId));
    }

    @Override
    public List<ReviewDTO> findByRaterUserId(UUID raterUserId) {
        if(!userRepository.existsById(raterUserId)){
            throw new NotFoundException("User not found by id: " + raterUserId);
        }
        return INSTANCE.mapToReviewDTOList(reviewRepository.findAllByRaterUser_Id(raterUserId));
    }

    @Override
    public ReviewDTO findById(UUID id) {
        return INSTANCE.mapToReviewDTO(reviewRepository.findById(id).orElseThrow(
                () -> {
                    throw new NotFoundException("Review not found by id: " + id);
                }
        ));
    }

    @Override
    public ReviewDTO save(CreateReviewForm reviewForm) {
        if(!userRepository.existsById(reviewForm.getRatedUserId())){
            throw new NotFoundException("User not found by id: " + reviewForm.getRatedUserId());
        }
        if(!userRepository.existsById(reviewForm.getRaterUserId())){
            throw new NotFoundException("User not found by id: " + reviewForm.getRaterUserId());
        }
        Review reviewToSave = customReviewMapper.mapToReview(reviewForm);
        try {
            return INSTANCE.mapToReviewDTO(reviewRepository.save(reviewToSave));
        } catch (DataAccessException dae) {
            throw new CouldNotCreateException("Internal Error. Could not create a new review.");
        }
    }

    @Override
    public ReviewDTO update(UUID id, UpdateReviewForm reviewToUpdate) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Review not found by id: " + id);
        });
        review.setRating(reviewToUpdate.getRating());
        review.setComment(reviewToUpdate.getComment());
        review.setAnonymous(reviewToUpdate.isAnonymous());
        try {
            return INSTANCE.mapToReviewDTO(reviewRepository.save(review));
        } catch (DataAccessException dae) {
            throw new CouldNotUpdateException("Internal Error. Could not update a review.");
        }
    }

    @Override
    public boolean delete(UUID id) {
        if (reviewRepository.deleteById(id) != 1)
            throw new CouldNotDeleteException("Internal Error. Could not delete a review.");
        return true;
    }

}
