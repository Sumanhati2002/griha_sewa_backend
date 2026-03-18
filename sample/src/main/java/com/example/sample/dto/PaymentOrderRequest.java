package com.example.sample.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
public class PaymentOrderRequest {
    @NotNull(message = "Amount is required")
    private Integer amount; // in lowest denomination (e.g., paisa)

    private String currency = "INR";
    private String receiptId;

    @NotNull(message = "User ID is required")
    private String userId;
}
