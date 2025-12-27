package com.example.sample.dto;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListingResponse {
    private Long id;
    private String title;
    private String description;
    private String category;
    private String villageName;
    private String contactNumber;
    private String status;
    private boolean needService;
    private String mobileNumber;
    private java.time.LocalDate serviceDate;
    private Double latitude;
    private Double longitude;
    private java.time.LocalDate date;

}
