package com.savicsoft.carpooling.googleapi;


import com.google.maps.GeoApiContext;
import com.google.maps.PlaceAutocompleteRequest;
import com.google.maps.PlacesApi;
import com.google.maps.model.AutocompletePrediction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class googleApiService {
    @Value("${google.api.key}")
    private String apiKey;
    PlaceAutocompleteRequest.SessionToken sessionToken = new PlaceAutocompleteRequest.SessionToken(UUID.randomUUID());


    public List<AutocompletePrediction> getPlaceAutocompletePredictions(String input) throws Exception {
        GeoApiContext context = new GeoApiContext.Builder().apiKey(apiKey).build();
        AutocompletePrediction[] autocomplete = PlacesApi.placeAutocomplete(context, input, sessionToken).await();

        if (autocomplete != null) {
            return Arrays.asList(autocomplete);
        }

        return Collections.emptyList();
    }
    public Mono<String> getRoute(String origin,String destination) {
        WebClient webClient = WebClient.create("https://maps.googleapis.com/maps/api/directions/json");

        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("origin", origin)
                        .queryParam("destination", destination)
                        .queryParam("key", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(Exception.class, e -> Mono.just("An error occurred: " + e.getMessage()));
    }

}

