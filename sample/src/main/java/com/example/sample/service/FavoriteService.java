package com.example.sample.service;

import com.example.sample.dto.FavoriteRequest;
import com.example.sample.dto.ListingResponse;

import java.util.List;

public interface FavoriteService {
    String addFavorite(FavoriteRequest request);
    String removeFavorite(FavoriteRequest request);
    List<ListingResponse> getUserFavorites(String userId);
}
