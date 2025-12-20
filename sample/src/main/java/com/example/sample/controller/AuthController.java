package com.example.sample.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final com.example.sample.service.UserService userService;
    private final com.example.sample.util.JwtUtil jwtUtil;

    public AuthController(com.example.sample.service.UserService userService, com.example.sample.util.JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @jakarta.validation.Valid @RequestBody com.example.sample.dto.AuthRequest request) {
        try {
            com.example.sample.entity.User user = userService.register(request.getMobileNumber(), request.getName());
            String token = jwtUtil.generateToken(user.getMobileNumber());
            com.example.sample.dto.LoginResponse response = new com.example.sample.dto.LoginResponse(
                    "Registration successful", new com.example.sample.dto.LoginResponse.Data(token, user));
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(java.util.Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@jakarta.validation.Valid @RequestBody com.example.sample.dto.AuthRequest request) {
        try {
            com.example.sample.entity.User user = userService.login(request.getMobileNumber());
            String token = jwtUtil.generateToken(user.getMobileNumber());
            com.example.sample.dto.LoginResponse response = new com.example.sample.dto.LoginResponse("Login successful",
                    new com.example.sample.dto.LoginResponse.Data(token, user));
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.status(401).body(java.util.Map.of("message", "Invalid token"));
        }
        java.util.Map<String, Object> response = new java.util.HashMap<>();
        response.put("message", "Logout successful");
        response.put("data", null);
        return ResponseEntity.ok(response);
    }
}
