package com.example.sample.service;

import com.example.sample.dto.ListingRequest;
import com.example.sample.dto.ListingResponse;
import java.util.List;

public interface ListingService {
    ListingResponse createListing(ListingRequest request);

    List<ListingResponse> getAllListings();

    List<ListingResponse> getListingsNearby(Double userLat, Double userLon, Double radiusKm);

    List<ListingResponse> getListingsByUserId(String userId);

    void deleteListing(Long id);
}
