package com.example.sample.dto;

import lombok.Data;

@Data
public class ListingResponse {
    private Long id;
    private String title;
    private String description;
    private String category;
    private String villageName;
    private String contactNumber;
    private String status;
    private boolean needService;
}
