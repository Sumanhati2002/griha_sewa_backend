package com.example.sample.service_impl;

import com.example.sample.dto.FavoriteRequest;
import com.example.sample.dto.ListingResponse;
import com.example.sample.entity.Favorite;
import com.example.sample.entity.Listing;
import com.example.sample.repository.FavoriteRepository;
import com.example.sample.repository.ListingRepository;
import com.example.sample.service.FavoriteService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final ListingRepository listingRepository;

    public FavoriteServiceImpl(FavoriteRepository favoriteRepository, ListingRepository listingRepository) {
        this.favoriteRepository = favoriteRepository;
        this.listingRepository = listingRepository;
    }

    @Override
    public String addFavorite(FavoriteRequest request) {
        Optional<Favorite> existing = favoriteRepository.findByUserIdAndListingId(request.getUserId(), request.getListingId());
        if (existing.isPresent()) {
            return "Item already in favorites";
        }

        Optional<Listing> listingOpt = listingRepository.findById(request.getListingId());
        if (listingOpt.isEmpty()) {
            return "Listing not found";
        }

        Favorite favorite = new Favorite();
        favorite.setUserId(request.getUserId());
        favorite.setListing(listingOpt.get());
        favoriteRepository.save(favorite);

        return "Added to favorites successfully";
    }

    @Override
    public String removeFavorite(FavoriteRequest request) {
        Optional<Favorite> existing = favoriteRepository.findByUserIdAndListingId(request.getUserId(), request.getListingId());
        if (existing.isPresent()) {
            favoriteRepository.delete(existing.get());
            return "Removed from favorites successfully";
        }
        return "Item not found in favorites";
    }

    @Override
    public List<ListingResponse> getUserFavorites(String userId) {
        return favoriteRepository.findByUserId(userId).stream()
                .map(favorite -> mapToResponse(favorite.getListing()))
                .collect(Collectors.toList());
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
        response.setUserId(listing.getUserId());
        return response;
    }
}
