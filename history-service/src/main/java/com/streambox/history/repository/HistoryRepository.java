package com.streambox.history.repository;

import com.streambox.history.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HistoryRepository extends JpaRepository<History, Long> {
    Optional<History> findByUserIdAndMovieId(
            Long userId,
            Long movieId
    );

    List<History> findByUserIdOrderByWatchedAtDesc(
            Long userId
    );

    void deleteByUserIdAndMovieId(
            Long userId,
            Long movieId
    );
}
