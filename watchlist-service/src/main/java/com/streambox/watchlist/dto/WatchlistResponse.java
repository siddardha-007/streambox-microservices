package com.streambox.watchlist.dto;

import java.time.LocalDateTime;

public record WatchlistResponse(
        Long id,
        Long userId,
        Long movieId,
        LocalDateTime addedAt
) {
}
