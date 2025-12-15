package com.example.sample.controller;

import com.example.sample.dto.ListingRequest;
import com.example.sample.dto.ListingResponse;
import com.example.sample.service.ListingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.example.sample.dto.SingleListingResponse;

@RestController
@RequestMapping("/api/listings")
public class ListingController {

    private final ListingService listingService;

    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }

    @PostMapping
    public ResponseEntity<SingleListingResponse> createListing(@RequestBody ListingRequest request) {
        ListingResponse listing = listingService.createListing(request);
        return ResponseEntity.ok(new SingleListingResponse(listing));
    }

    @GetMapping
    public ResponseEntity<List<ListingResponse>> getAllListings() {
        return ResponseEntity.ok(listingService.getAllListings());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteListing(@PathVariable Long id) {
        listingService.deleteListing(id);
        return ResponseEntity.noContent().build();
    }
}
