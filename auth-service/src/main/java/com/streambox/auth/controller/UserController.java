package com.streambox.auth.controller;

import com.streambox.auth.service.AuthService;
import com.streambox.auth.service.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final AuthService authService;

    @GetMapping("/me")
    public ResponseEntity<CustomUserDetails> getCurrentUser(
            Authentication authentication
    ) {

        CustomUserDetails user =
                authService.getCurrentUser(authentication);

        return ResponseEntity.ok(user);
    }
}
