package com.streambox.watchlist.controller;

import com.streambox.watchlist.dto.AddToWatchlistRequest;
import com.streambox.watchlist.dto.WatchlistResponse;
import com.streambox.watchlist.service.WatchlistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/watchlist")
public class WatchlistController {
    private final WatchlistService watchlistService;

    @PostMapping
    public ResponseEntity<WatchlistResponse> addToWatchlist(
            @RequestParam Long userId,
            @Valid @RequestBody AddToWatchlistRequest request
    ) {

        WatchlistResponse response =
                watchlistService.addToWatchlist(userId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<WatchlistResponse>> getUserWatchlist(
            @RequestParam Long userId
    ) {

        return ResponseEntity.ok(
                watchlistService.getUserWatchlist(userId)
        );
    }

    @DeleteMapping("/{movieId}")
    public ResponseEntity<Void> removeFromWatchlist(
            @RequestParam Long userId,
            @PathVariable Long movieId
    ) {

        watchlistService.removeFromWatchlist(
                userId,
                movieId
        );

        return ResponseEntity.noContent().build();
    }
}
