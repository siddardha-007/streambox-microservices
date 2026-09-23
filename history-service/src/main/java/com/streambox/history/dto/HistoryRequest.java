package com.streambox.history.dto;

import jakarta.validation.constraints.NotNull;

public record HistoryRequest(
        @NotNull
        Long movieId
) {
}
