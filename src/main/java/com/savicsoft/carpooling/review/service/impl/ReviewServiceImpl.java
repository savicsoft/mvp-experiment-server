package com.savicsoft.carpooling.review.service.impl;

import com.savicsoft.carpooling.exception.*;
import com.savicsoft.carpooling.review.model.dto.ReviewDTO;
import com.savicsoft.carpooling.review.model.entity.Review;
import com.savicsoft.carpooling.review.model.form.CreateReviewForm;
import com.savicsoft.carpooling.review.model.form.UpdateReviewForm;
import com.savicsoft.carpooling.review.model.mapper.CustomReviewMapper;
import com.savicsoft.carpooling.review.repository.ReviewRepository;
import com.savicsoft.carpooling.review.service.ReviewService;
import com.savicsoft.carpooling.security.auth.AuthUtil;
import com.savicsoft.carpooling.user.model.entity.User;
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
    private final AuthUtil authUtil;

    @Override
    public List<ReviewDTO> findByRatedUserId(UUID ratedUserId) {
        //will rewrite to existsById from userService
        if (!userRepository.existsById(ratedUserId)) {
            throw new NotFoundException("User not found by id: " + ratedUserId);
        }
        return INSTANCE.mapToReviewDTOList(reviewRepository.findAllByRatedUser_Id(ratedUserId));
    }

    @Override
    public List<ReviewDTO> findByRaterUser() {
        String username = authUtil.getNameFromContext();
        return INSTANCE.mapToReviewDTOList(reviewRepository.findAllByRaterUser_Email(username));
    }

    @Override
    public ReviewDTO findById(UUID id) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Review not found by id: " + id);
        });
        if (!review.getRaterUser().getUsername().equals(authUtil.getNameFromContext())) {
            throw new ForbiddenException("You are not allowed to access this data!");
        }

        return INSTANCE.mapToReviewDTO(review);
    }

    @Override
    public ReviewDTO save(CreateReviewForm reviewForm) {
        if (!userRepository.existsById(reviewForm.getRatedUserId())) {
            throw new NotFoundException("User not found by id: " + reviewForm.getRatedUserId());
        }

        String username = authUtil.getNameFromContext();
        User user = userRepository.getUserByEmail(username).orElseThrow(() -> {
            throw new NotFoundException("User not found by email: " + username);
        });

        Review reviewToSave = customReviewMapper.mapToReview(reviewForm);
        reviewToSave.setRaterUser(user);

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

        if (!review.getRaterUser().getUsername().equals(authUtil.getNameFromContext())) {
            throw new ForbiddenException("You are not allowed to access this data!");
        }

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
        Review review = reviewRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Review not found by id: " + id);
        });

        if (!review.getRaterUser().getUsername().equals(authUtil.getNameFromContext())) {
            throw new ForbiddenException("You are not allowed to access this data!");
        }

        try {
            reviewRepository.delete(review);
        } catch (Exception e) {
            throw new CouldNotDeleteException("Internal Error. Could not delete a review.");
        }
        return true;
    }

}
