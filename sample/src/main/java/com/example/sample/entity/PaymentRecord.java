package com.example.sample.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "payment_records")
public class PaymentRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    
    @Column(unique = true)
    private String razorpayOrderId;

    private String razorpayPaymentId;

    private Integer amount;
    private String currency;

    private String status; // PENDING, SUCCESS, FAILED

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime expiryDate;
}
