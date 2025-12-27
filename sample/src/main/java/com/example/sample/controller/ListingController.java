package com.example.sample.controller;

import com.example.sample.dto.ListingRequest;
import com.example.sample.dto.ListingResponse;
import com.example.sample.dto.ListingListResponse;
import com.example.sample.service.ListingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.sample.dto.SingleListingResponse;

@RestController
@RequestMapping("/api/listings")
public class ListingController {

    private final ListingService listingService;

    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }

    @PostMapping
    public ResponseEntity<?> createListing(
            @jakarta.validation.Valid @RequestBody ListingRequest request) {
        try {
            ListingResponse listing = listingService.createListing(request);
            return ResponseEntity.ok(new SingleListingResponse(listing));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(java.util.Map.of("message", "An unexpected error occurred"));
        }
    }

    @GetMapping
    public ResponseEntity<ListingListResponse> getAllListings(
            @RequestParam(required = false) Double latitude,
            @RequestParam(required = false) Double longitude,
            @RequestParam(defaultValue = "50.0") Double radius) {

        // If location provided, filter by distance
        if (latitude != null && longitude != null) {
            return ResponseEntity.ok(new ListingListResponse(
                    listingService.getListingsNearby(latitude, longitude, radius)));
        }

        // Otherwise return all listings
        return ResponseEntity.ok(new ListingListResponse(listingService.getAllListings()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteListing(@PathVariable Long id) {
        listingService.deleteListing(id);
        return ResponseEntity.noContent().build();
    }
}
