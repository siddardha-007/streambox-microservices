package com.streambox.history.service;

import com.streambox.history.client.MovieClient;
import com.streambox.history.dto.HistoryRequest;
import com.streambox.history.dto.HistoryResponse;
import com.streambox.history.dto.MovieResponse;
import com.streambox.history.entity.History;
import com.streambox.history.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HistoryService {
    private final HistoryRepository historyRepository;

    private final MovieClient movieClient;

    public HistoryResponse addToHistory(
            Long userId,
            HistoryRequest request
    ) {

        Optional<History> existingHistory =
                historyRepository.findByUserIdAndMovieId(
                        userId,
                        request.movieId()
                );

        History history;

        if (existingHistory.isPresent()) {

            history = existingHistory.get();
            history.setWatchedAt(LocalDateTime.now());

        } else {
            MovieResponse movie =
                    movieClient.getMovieById(request.movieId());

            history = History.builder()
                    .userId(userId)
                    .movieId(movie.id())
                    .watchedAt(LocalDateTime.now())
                    .build();
        }

        History saved = historyRepository.save(history);

        return mapToResponse(saved);
    }

    public List<HistoryResponse> getMyHistory(Long userId) {

        return historyRepository
                .findByUserIdOrderByWatchedAtDesc(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public void removeFromHistory(
            Long userId,
            Long movieId
    ) {

        History history =
                historyRepository
                        .findByUserIdAndMovieId(userId, movieId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Movie not found in history"
                                )
                        );

        historyRepository.delete(history);
    }

    private HistoryResponse mapToResponse(History history) {

        return new HistoryResponse(
                history.getId(),
                history.getUserId(),
                history.getMovieId(),
                history.getWatchedAt()
        );
    }
}
