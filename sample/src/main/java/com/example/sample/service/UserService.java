package com.example.sample.service;

import com.example.sample.entity.User;

public interface UserService {
    User saveMobileNumber(String mobileNumber);

    User verifyOtp(String mobileNumber, String otp);
}
