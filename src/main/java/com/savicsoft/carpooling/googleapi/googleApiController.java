package com.savicsoft.carpooling.googleapi;

import com.google.maps.model.AutocompletePrediction;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@NoArgsConstructor
@AllArgsConstructor
public class googleApiController {
    private googleApiService googleApiService;
    @GetMapping("/autocomplete")
    public List<AutocompletePrediction> autocomplete(@RequestParam String input) throws Exception {
        return googleApiService.getPlaceAutocompletePredictions(input);
    }
}
