package com.streambox.history.dto;

import java.time.LocalDateTime;

public record HistoryResponse(
        Long id,
        Long userId,
        Long movieId,
        LocalDateTime watchedAt
) {
}
