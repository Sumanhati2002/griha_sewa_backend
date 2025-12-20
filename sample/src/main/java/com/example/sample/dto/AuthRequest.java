package com.example.sample.dto;

import lombok.Data;

@Data
public class AuthRequest {
    @jakarta.validation.constraints.NotBlank(message = "Mobile number is required")
    @jakarta.validation.constraints.Size(min = 10, max = 15, message = "Mobile number must be between 10 and 15 characters")
    private String mobileNumber;

    @jakarta.validation.constraints.NotBlank(message = "Name is required for registration", groups = Registration.class)
    private String name;

    public interface Registration {
    }

}
