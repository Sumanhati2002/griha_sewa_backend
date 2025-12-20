package com.example.sample.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "listings")
public class Listing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String category;
    private String villageName;
    private String contactNumber;

    @Column(nullable = false)
    private String status; // NEED or OFFER

    private Long userId;

    private java.time.LocalDate createdAt;
}
