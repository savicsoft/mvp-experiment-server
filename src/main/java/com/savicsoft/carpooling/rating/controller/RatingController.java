package com.savicsoft.carpooling.rating.controller;

import com.savicsoft.carpooling.rating.form.RatingForm;
import jakarta.validation.Valid;
import com.savicsoft.carpooling.domain.HttpResponse;
import com.savicsoft.carpooling.rating.dto.RatingDTO;
import com.savicsoft.carpooling.rating.service.RatingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@RequestMapping("/api/v1/rating")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @GetMapping("/all")
    public ResponseEntity<HttpResponse> getAllRatings(){
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

    @GetMapping("/list/{UUID}")
    public ResponseEntity<HttpResponse> getAllByUserUUID (@PathVariable("UUID") UUID uuid) {
        List<RatingDTO> ratings = ratingService.findByRatingUser_Uuid(uuid);
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("ratings", ratings))
                        .message("All user ratings retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );

    }

    //TODO: Method should be implemented using the Authentication. this is an example how to implement.
    @GetMapping("/rated_list/{UUID}")
    //public ResponseEntity<HttpResponse> getAllRatingsLeftByUser(Authentication auth){
    public ResponseEntity<HttpResponse> getAllRatingsLeftByUser(@PathVariable("UUID") UUID uuid){
        //UserDTO user = (UserDTO) auth.getPrincipal();
        //List<RatingDTO> ratings = ratingService.findByRatingUser_Uuid(user.getUUID);
        List<RatingDTO> ratings = ratingService.findByRatingUser_Uuid(uuid);

        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("ratings", ratings))
                        .message("All user ratings retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
   }

   @GetMapping("/{id}")
   public ResponseEntity<HttpResponse> getRatingById(@PathVariable("id") Long id){

        Optional<RatingDTO> rating = ratingService.findById(id);
        if (rating.isPresent()){
            return ResponseEntity.ok(
                    HttpResponse.builder()
                            .timeStamp(LocalDateTime.now().toString())
                            .data(Map.of("ratings",rating))
                            .message("Rating retrieved")
                            .status(OK)
                            .statusCode(OK.value())
                            .build()
            );
        }else{
                return new ResponseEntity<>(
                        HttpResponse.builder()
                            .timeStamp(LocalDateTime.now().toString())
                            .message("There was no rating found")
                            .developerMessage("There is no rating with the given id")
                            .status(BAD_REQUEST)
                            .statusCode(BAD_REQUEST.value())
                            .build(), BAD_REQUEST);
        }
   }


    @GetMapping("/get/{UUID}")
    public ResponseEntity<HttpResponse> getRatingByUUID(@PathVariable("UUID") UUID UUID){

        Optional<RatingDTO> rating = ratingService.findByUuid(UUID);
        if (rating.isPresent()){
            return ResponseEntity.ok(
                    HttpResponse.builder()
                            .timeStamp(LocalDateTime.now().toString())
                            .data(Map.of("ratings",rating))
                            .message("Rating retrieved")
                            .status(OK)
                            .statusCode(OK.value())
                            .build()
            );
        }else{
            return new ResponseEntity<>(
                    HttpResponse.builder()
                            .timeStamp(LocalDateTime.now().toString())
                            .message("There was no rating found")
                            .developerMessage("There is no rating with the given UUID")
                            .status(BAD_REQUEST)
                            .statusCode(BAD_REQUEST.value())
                            .build(), BAD_REQUEST);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<HttpResponse> getAllUserRatingsByRatingType(@RequestBody @Valid RatingForm ratingForm){

        List<RatingDTO> ratings = ratingService.findByRatingUser_UuidAndRatingType(
                ratingForm.getUuid(), ratingForm.getRatingType()
        );

        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .message("All user ratings retrieved")
                        .data(Map.of("ratings",ratings))
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }





}


