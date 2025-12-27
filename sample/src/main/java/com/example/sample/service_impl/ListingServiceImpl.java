package com.example.sample.service_impl;

import com.example.sample.dto.ListingRequest;
import com.example.sample.dto.ListingResponse;
import com.example.sample.entity.Listing;
import com.example.sample.repository.ListingRepository;
import com.example.sample.service.ListingService;
import com.example.sample.service.GeocodingService;
import com.example.sample.util.DistanceCalculator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListingServiceImpl implements ListingService {

    private final ListingRepository listingRepository;
    private final GeocodingService geocodingService;

    public ListingServiceImpl(ListingRepository listingRepository, GeocodingService geocodingService) {
        this.listingRepository = listingRepository;
        this.geocodingService = geocodingService;
    }

    @Override
    public ListingResponse createListing(ListingRequest request) {
        Listing listing = new Listing();
        listing.setTitle(request.getTitle());
        listing.setDescription(request.getDescription());
        listing.setCategory(request.getCategory());
        listing.setVillageName(request.getVillageName());
        listing.setContactNumber(request.getContactNumber());
        listing.setMobileNumber(request.getMobileNumber());
        listing.setDeviceId(request.getDeviceId());
        listing.setServiceDate(request.getServiceDate());
        listing.setCreatedAt(java.time.LocalDate.now());

        // Geocode village name to get coordinates
        Double[] coordinates = geocodingService.geocodeVillage(request.getVillageName());
        if (coordinates != null) {
            listing.setLatitude(coordinates[0]);
            listing.setLongitude(coordinates[1]);
        }

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
    public List<ListingResponse> getListingsNearby(Double userLat, Double userLon, Double radiusKm) {
        return listingRepository.findAll().stream()
                .filter(listing -> {
                    if (listing.getLatitude() == null || listing.getLongitude() == null) {
                        return false; // Skip listings without coordinates
                    }
                    double distance = DistanceCalculator.calculateDistance(
                            userLat, userLon,
                            listing.getLatitude(), listing.getLongitude());
                    return distance <= radiusKm;
                })
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ListingResponse> getListingsByDeviceId(String deviceId) {
        return listingRepository.findByDeviceId(deviceId).stream()
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
        response.setMobileNumber(listing.getMobileNumber());
        response.setServiceDate(listing.getServiceDate());
        response.setLatitude(listing.getLatitude());
        response.setLongitude(listing.getLongitude());
        response.setStatus(listing.getStatus());
        response.setNeedService("NEED".equalsIgnoreCase(listing.getStatus()));
        response.setDate(listing.getCreatedAt());
        response.setDeviceId(listing.getDeviceId());
        return response;
    }
}
