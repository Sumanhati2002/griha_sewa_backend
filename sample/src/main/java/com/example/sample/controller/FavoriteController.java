package com.example.sample.controller;

import com.example.sample.dto.FavoriteRequest;
import com.example.sample.dto.ListingResponse;
import com.example.sample.service.FavoriteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addFavorite(@Valid @RequestBody FavoriteRequest request) {
        String result = favoriteService.addFavorite(request);
        if (result.contains("successfully")) {
            return ResponseEntity.ok(Map.of("message", result));
        } else {
            return ResponseEntity.status(400).body(Map.of("message", result));
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> removeFavorite(@Valid @RequestBody FavoriteRequest request) {
        String result = favoriteService.removeFavorite(request);
        if (result.contains("successfully")) {
            return ResponseEntity.ok(Map.of("message", result));
        } else {
            return ResponseEntity.status(400).body(Map.of("message", result));
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<ListingResponse>> getUserFavorites(@PathVariable String userId) {
        return ResponseEntity.ok(favoriteService.getUserFavorites(userId));
    }
}
