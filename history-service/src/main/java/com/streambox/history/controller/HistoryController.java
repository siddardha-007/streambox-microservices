package com.streambox.history.controller;

import com.streambox.history.dto.HistoryRequest;
import com.streambox.history.dto.HistoryResponse;
import com.streambox.history.service.HistoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/history")
public class HistoryController {
    private final HistoryService historyService;

    @PostMapping
    public ResponseEntity<HistoryResponse> addToHistory(
            Authentication authentication,
            @Valid @RequestBody HistoryRequest request
    ) {

        Long userId = (Long) authentication.getPrincipal();

        HistoryResponse response =
                historyService.addToHistory(
                        userId,
                        request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<List<HistoryResponse>> getMyHistory(
            Authentication authentication
    ) {

        Long userId = (Long) authentication.getPrincipal();

        return ResponseEntity.ok(
                historyService.getMyHistory(userId)
        );
    }

    @DeleteMapping("/{movieId}")
    public ResponseEntity<Void> removeFromHistory(
            Authentication authentication,
            @PathVariable Long movieId
    ) {

        Long userId = (Long) authentication.getPrincipal();

        historyService.removeFromHistory(
                userId,
                movieId
        );

        return ResponseEntity.noContent().build();
    }
}
