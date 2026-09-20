package com.streambox.watchlist.dto;

import jakarta.validation.constraints.NotNull;

public record AddToWatchlistRequest(
        @NotNull
        Long movieId
) {
}
