package org.geeksforgeeks.digitalwallet.controller;

import lombok.RequiredArgsConstructor;
import org.geeksforgeeks.digitalwallet.dto.LoginRequest;
import org.geeksforgeeks.digitalwallet.dto.RegisterRequest;
import org.geeksforgeeks.digitalwallet.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.geeksforgeeks.digitalwallet.dto.AuthResponse;
import org.geeksforgeeks.digitalwallet.dto.ErrorResponse;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            String token = this.userService.registerUser(request);
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            String token = this.userService.loginUser(request);
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Invalid credentials or user not found"));
        }
    }
}
