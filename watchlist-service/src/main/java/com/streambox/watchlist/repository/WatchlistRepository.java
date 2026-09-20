package com.streambox.watchlist.repository;

import com.streambox.watchlist.entity.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {
    List<Watchlist> findByUserId(Long userId);

    Optional<Watchlist> findByUserIdAndMovieId(
            Long userId,
            Long movieId
    );

    boolean existsByUserIdAndMovieId(
            Long userId,
            Long movieId
    );

    void deleteByUserIdAndMovieId(
            Long userId,
            Long movieId
    );
}
