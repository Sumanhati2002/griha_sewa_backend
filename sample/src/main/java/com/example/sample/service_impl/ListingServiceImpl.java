package com.example.sample.service_impl;

import com.example.sample.dto.ListingRequest;
import com.example.sample.dto.ListingResponse;
import com.example.sample.entity.Listing;
import com.example.sample.repository.ListingRepository;
import com.example.sample.service.ListingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListingServiceImpl implements ListingService {

    private final ListingRepository listingRepository;

    public ListingServiceImpl(ListingRepository listingRepository) {
        this.listingRepository = listingRepository;
    }

    @Override
    public ListingResponse createListing(ListingRequest request) {
        Listing listing = new Listing();
        listing.setTitle(request.getTitle());
        listing.setDescription(request.getDescription());
        listing.setCategory(request.getCategory());
        listing.setVillageName(request.getVillageName());
        listing.setContactNumber(request.getContactNumber());
        listing.setUserId(request.getUserId());
        listing.setCreatedAt(java.time.LocalDate.now());

        // Logic: if need service -> NEED, else OFFER
        if (request.isNeedService()) {
            listing.setStatus("NEED");
        } else {
            listing.setStatus("OFFER");
        }

        Listing savedListing = listingRepository.save(listing);
        return mapToResponse(savedListing);
    }

    @Override
    public List<ListingResponse> getAllListings() {
        return listingRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ListingResponse> getListingsByUserId(Long userId) {
        return listingRepository.findByUserId(userId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteListing(Long id) {
        listingRepository.deleteById(id);
    }

    private ListingResponse mapToResponse(Listing listing) {
        ListingResponse response = new ListingResponse();
        response.setId(listing.getId());
        response.setTitle(listing.getTitle());
        response.setDescription(listing.getDescription());
        response.setCategory(listing.getCategory());
        response.setVillageName(listing.getVillageName());
        response.setContactNumber(listing.getContactNumber());
        response.setStatus(listing.getStatus());
        response.setNeedService("NEED".equalsIgnoreCase(listing.getStatus()));
        response.setDate(listing.getCreatedAt());
        response.setUserId(listing.getUserId());
        return response;
    }
}
