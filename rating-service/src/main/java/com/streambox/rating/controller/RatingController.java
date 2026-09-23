package com.streambox.rating.controller;

import com.streambox.rating.dto.CreateRatingRequest;
import com.streambox.rating.dto.RatingResponse;
import com.streambox.rating.service.RatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
@RequiredArgsConstructor
public class RatingController {
    private final RatingService ratingService;

    @PostMapping
    public ResponseEntity<RatingResponse> addRating(
            Authentication authentication,
            @Valid @RequestBody CreateRatingRequest request
    ) {

        Long userId = (Long) authentication.getPrincipal();

        RatingResponse response =
                ratingService.addRating(userId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<RatingResponse>> getMovieRatings(
            @PathVariable Long movieId
    ) {

        return ResponseEntity.ok(
                ratingService.getMovieRatings(movieId)
        );
    }

    @GetMapping("/me")
    public ResponseEntity<List<RatingResponse>> getMyRatings(
            Authentication authentication
    ) {

        Long userId = (Long) authentication.getPrincipal();

        return ResponseEntity.ok(
                ratingService.getMyRatings(userId)
        );
    }

    @PutMapping("/{movieId}")
    public ResponseEntity<RatingResponse> updateRating(
            Authentication authentication,
            @PathVariable Long movieId,
            @Valid @RequestBody CreateRatingRequest request
    ) {

        Long userId = (Long) authentication.getPrincipal();

        return ResponseEntity.ok(
                ratingService.updateRating(
                        userId,
                        movieId,
                        request
                )
        );
    }

    @DeleteMapping("/{movieId}")
    public ResponseEntity<Void> deleteRating(
            Authentication authentication,
            @PathVariable Long movieId
    ) {

        Long userId = (Long) authentication.getPrincipal();

        ratingService.deleteRating(userId, movieId);

        return ResponseEntity.noContent().build();
    }
}
