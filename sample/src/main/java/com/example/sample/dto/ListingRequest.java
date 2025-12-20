package com.example.sample.dto;

import lombok.Data;

@Data
public class ListingRequest {
    @jakarta.validation.constraints.NotBlank(message = "Title is required")
    private String title;

    @jakarta.validation.constraints.NotBlank(message = "Description is required")
    private String description;

    @jakarta.validation.constraints.NotBlank(message = "Category is required")
    private String category;

    @jakarta.validation.constraints.NotBlank(message = "Village name is required")
    private String villageName;

    @jakarta.validation.constraints.NotBlank(message = "Contact number is required")
    private String contactNumber;

    private boolean isNeedService; // true = NEED, false = OFFER

    private Long userId;
}
