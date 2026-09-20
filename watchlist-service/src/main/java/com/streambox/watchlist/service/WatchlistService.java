package com.streambox.watchlist.service;

import com.streambox.watchlist.client.MovieClient;
import com.streambox.watchlist.dto.AddToWatchlistRequest;
import com.streambox.watchlist.dto.MovieResponse;
import com.streambox.watchlist.dto.WatchlistResponse;
import com.streambox.watchlist.entity.Watchlist;
import com.streambox.watchlist.repository.WatchlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WatchlistService {
    private final WatchlistRepository watchlistRepository;

    private final MovieClient movieClient;

    public WatchlistResponse addToWatchlist(
            Long userId,
            AddToWatchlistRequest request
    ) {

        MovieResponse movie =
                movieClient.getMovieById(request.movieId());

        if (watchlistRepository.existsByUserIdAndMovieId(
                userId,
                request.movieId()
        )) {
            throw new RuntimeException(
                    "Movie already exists in watchlist"
            );
        }

        Watchlist watchlist = Watchlist.builder()
                .userId(userId)
                .movieId(request.movieId())
                .addedAt(LocalDateTime.now())
                .build();

        Watchlist saved = watchlistRepository.save(watchlist);

        return mapToResponse(saved);
    }

    public List<WatchlistResponse> getUserWatchlist(
            Long userId
    ) {

        return watchlistRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    @Transactional
    public void removeFromWatchlist(
            Long userId,
            Long movieId
    ) {

        if (!watchlistRepository.existsByUserIdAndMovieId(
                userId,
                movieId
        )) {
            throw new RuntimeException(
                    "Movie not found in watchlist"
            );
        }

        watchlistRepository.deleteByUserIdAndMovieId(
                userId,
                movieId
        );
    }

    //helper

    private WatchlistResponse mapToResponse(
            Watchlist watchlist
    ) {

        return new WatchlistResponse(
                watchlist.getId(),
                watchlist.getUserId(),
                watchlist.getMovieId(),
                watchlist.getAddedAt()
        );
    }
}
