package com.example.sample.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OtpService {

    // Simulating a database/cache for OTP storage
    private final Map<String, String> otpStorage = new HashMap<>();
    private final Random random = new Random();

    public String sendOtp(String mobileNumber) {
        // Generate a 4-digit OTP
        String otp = String.format("%04d", random.nextInt(10000));

        // Store the OTP
        otpStorage.put(mobileNumber, otp);

        // Log the OTP (In a real app, send via SMS)
        System.out.println("OTP for " + mobileNumber + ": " + otp);

        return otp;
    }

    public boolean verifyOtp(String mobileNumber, String otp) {
        if (!otpStorage.containsKey(mobileNumber)) {
            return false;
        }

        String storedOtp = otpStorage.get(mobileNumber);
        return storedOtp.equals(otp);
    }
}
