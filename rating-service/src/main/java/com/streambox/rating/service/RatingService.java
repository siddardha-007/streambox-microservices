package com.streambox.rating.service;

import com.streambox.rating.client.MovieClient;
import com.streambox.rating.dto.CreateRatingRequest;
import com.streambox.rating.dto.MovieResponse;
import com.streambox.rating.dto.RatingResponse;
import com.streambox.rating.entity.Rating;
import com.streambox.rating.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;

    private final MovieClient movieClient;

    public RatingResponse addRating(
            Long userId,
            CreateRatingRequest request
    ) {

        // 1. Check movie exists
        MovieResponse movie =
                movieClient.getMovieById(request.movieId());

        if (ratingRepository.existsByUserIdAndMovieId(
                userId,
                request.movieId()
        )) {
            throw new RuntimeException(
                    "You have already rated this movie"
            );
        }

        Rating rating = Rating.builder()
                .userId(userId)
                .movieId(movie.id())
                .score(request.score())
                .comment(request.comment())
                .createdAt(LocalDateTime.now())
                .build();

        Rating saved = ratingRepository.save(rating);

        return mapToResponse(saved);
    }

    public List<RatingResponse> getMovieRatings(Long movieId) {

        return ratingRepository.findByMovieId(movieId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<RatingResponse> getMyRatings(Long userId) {

        return ratingRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public RatingResponse updateRating(
            Long userId,
            Long movieId,
            CreateRatingRequest request
    ) {

        Rating rating = ratingRepository
                .findByUserIdAndMovieId(userId, movieId)
                .orElseThrow(() ->
                        new RuntimeException("Rating not found")
                );

        rating.setScore(request.score());
        rating.setComment(request.comment());
        rating.setUpdatedAt(LocalDateTime.now());

        Rating updated = ratingRepository.save(rating);

        return mapToResponse(updated);
    }



    public void deleteRating(Long userId, Long movieId) {

        Rating rating = ratingRepository
                .findByUserIdAndMovieId(userId, movieId)
                .orElseThrow(() ->
                        new RuntimeException("Rating not found")
                );

        ratingRepository.delete(rating);
    }

    private RatingResponse mapToResponse(Rating rating) {

        return new RatingResponse(
                rating.getId(),
                rating.getUserId(),
                rating.getMovieId(),
                rating.getScore(),
                rating.getComment(),
                rating.getCreatedAt(),
                rating.getUpdatedAt()
        );
    }

}
