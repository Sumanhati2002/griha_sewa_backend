package com.example.sample.dto;

import lombok.Data;

@Data
public class ListingRequest {
    private String title;
    private String description;
    private String category;
    private String villageName;
    private String contactNumber;
    private boolean isNeedService; // true = NEED, false = OFFER
    private Long userId;
}
