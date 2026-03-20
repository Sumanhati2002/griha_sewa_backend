package com.example.sample.repository;

import com.example.sample.entity.PaymentRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRecordRepository extends JpaRepository<PaymentRecord, Long> {
    Optional<PaymentRecord> findByRazorpayOrderId(String razorpayOrderId);
    
    Optional<PaymentRecord> findFirstByUserIdAndStatusOrderByExpiryDateDesc(String userId, String status);
}
