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
    private final com.example.sample.service.UserService userService;
    private final com.example.sample.util.JwtUtil jwtUtil;

    public ListingController(ListingService listingService, com.example.sample.service.UserService userService,
            com.example.sample.util.JwtUtil jwtUtil) {
        this.listingService = listingService;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<?> createListing(
            @RequestHeader("Authorization") String token,
            @jakarta.validation.Valid @RequestBody ListingRequest request) {
        try {
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            String mobileNumber = jwtUtil.extractUsername(token);
            com.example.sample.entity.User user = userService.getUserByMobileNumber(mobileNumber);
            request.setUserId(user.getId());

            ListingResponse listing = listingService.createListing(request);
            return ResponseEntity.ok(new SingleListingResponse(listing));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(java.util.Map.of("message", "Unauthorized"));
        }
    }

    @GetMapping
    public ResponseEntity<ListingListResponse> getAllListings() {
        return ResponseEntity.ok(new ListingListResponse(listingService.getAllListings()));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ListingListResponse> getListingsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(new ListingListResponse(listingService.getListingsByUserId(userId)));
    }

    @GetMapping("/my-listings")
    public ResponseEntity<?> getMyListings(@RequestHeader("Authorization") String token) {
        try {
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            String mobileNumber = jwtUtil.extractUsername(token);
            com.example.sample.entity.User user = userService.getUserByMobileNumber(mobileNumber);
            return ResponseEntity.ok(new ListingListResponse(listingService.getListingsByUserId(user.getId())));
        } catch (Exception e) {
            return ResponseEntity.status(401).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteListing(@PathVariable Long id) {
        listingService.deleteListing(id);
        return ResponseEntity.noContent().build();
    }
}
