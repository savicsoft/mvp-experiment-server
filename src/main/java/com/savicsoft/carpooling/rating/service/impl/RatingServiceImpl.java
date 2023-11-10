package com.savicsoft.carpooling.rating.service.impl;

import com.savicsoft.carpooling.rating.dto.RatingDTO;
import com.savicsoft.carpooling.rating.enumeration.RatingType;
import com.savicsoft.carpooling.rating.repository.RatingRepository;
import com.savicsoft.carpooling.rating.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.savicsoft.carpooling.rating.dto.mapper.RatingDTOMapper.INSTANCE;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    @Override
    public List<RatingDTO> findAll() {
        return INSTANCE.mapToRatingDTOList(ratingRepository.findAll()).stream().map(
                e -> {
                    if (e.isAnonymous())
                        e.setRatingUser(null);
                    return e;
                }
        ).collect(Collectors.toList());
    }

    @Override
    public List<RatingDTO> findByUser_Uuid(UUID userUuid) {
        return INSTANCE.mapToRatingDTOList(ratingRepository.findByUser_Uuid(userUuid))
                .stream()
                .map(e -> {
                    if (e.isAnonymous())
                        e.setRatingUser(null);
                    return e;
                }).collect(Collectors.toList());
    }

    @Override
    public List<RatingDTO> findByRatingUser_Uuid(UUID ratingUserUuid) {
        return INSTANCE.mapToRatingDTOList(ratingRepository.findByRatingUser_Uuid(ratingUserUuid))
                .stream()
                .map(e -> {
                    if (e.isAnonymous())
                        e.setRatingUser(null);
                    return e;
                }).collect(Collectors.toList());
    }

    @Override
    public Optional<RatingDTO> findById(Long id) {
        RatingDTO ratingDTO = INSTANCE.mapToRatingDTO(ratingRepository.findById(id).orElseThrow(
                () ->{
                    throw new RuntimeException("There was no rating found");
                }
        ));
        if (ratingDTO.isAnonymous()){
            ratingDTO.setRatingUser(null);
        }
        return Optional.of(ratingDTO);
    }

    @Override
    public Optional<RatingDTO> findByUuid(UUID uuid) {
        RatingDTO ratingDTO = INSTANCE.mapToRatingDTO(ratingRepository.findByUuid(uuid).orElseThrow(
                () ->{
                    throw new RuntimeException("There was no rating found");
                }
        ));
        if (ratingDTO.isAnonymous()){
            ratingDTO.setRatingUser(null);
        }
        return Optional.of(ratingDTO);
    }

    @Override
    public List<RatingDTO> findByUser_UuidAndRatingType(UUID userUuid, RatingType ratingType) {
        return INSTANCE.mapToRatingDTOList(ratingRepository.findByUser_UuidAndRatingType(userUuid,ratingType))
                .stream().map(
                        e->{
                            if (e.isAnonymous()){
                                e.setRatingUser(null);
                            }
                            return e;
                        }
                ).collect(Collectors.toList());
    }

    @Override
    public List<RatingDTO> findByRatingUser_UuidAndRatingType(UUID ratingUserUuid, RatingType ratingType) {
        return INSTANCE.mapToRatingDTOList(ratingRepository.findByRatingUser_UuidAndRatingType(ratingUserUuid,ratingType))
                .stream().map(
                        e->{
                            if (e.isAnonymous()){
                                e.setRatingUser(null);
                            }
                            return e;
                        }
                ).collect(Collectors.toList());
    }
}
