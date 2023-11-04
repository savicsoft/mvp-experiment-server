package com.savicsoft.carpooling.rating.controller;

import com.savicsoft.carpooling.car.dto.CarDTO;
import com.savicsoft.carpooling.domain.HttpResponse;
import com.savicsoft.carpooling.rating.dto.RatingDTO;
import com.savicsoft.carpooling.rating.service.RatingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@RequestMapping("/api/v1/rating")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @GetMapping("/list")
    public ResponseEntity<HttpResponse> getAllCars(){
        List<RatingDTO> ratings =  ratingService.findAll();
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("ratings",ratings))
                        .message("All ratings are retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );

    }

}
