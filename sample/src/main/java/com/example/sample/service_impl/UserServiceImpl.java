package com.example.sample.service_impl;

import com.example.sample.entity.User;
import com.example.sample.repository.UserRepository;
import com.example.sample.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final com.example.sample.service.OtpService otpService;

    public UserServiceImpl(UserRepository userRepository, com.example.sample.service.OtpService otpService) {
        this.userRepository = userRepository;
        this.otpService = otpService;
    }

    @Override
    public User saveMobileNumber(String mobileNumber) {
        otpService.sendOtp(mobileNumber);
        Optional<User> existingUser = userRepository.findByMobileNumber(mobileNumber);
        if (existingUser.isPresent()) {
            return existingUser.get();
        }
        User newUser = new User();
        newUser.setMobileNumber(mobileNumber);
        return userRepository.save(newUser);
    }

    @Override
    public User verifyOtp(String mobileNumber, String otp) {
        boolean isValid = otpService.verifyOtp(mobileNumber, otp);
        if (isValid) {
            return userRepository.findByMobileNumber(mobileNumber).orElse(null);
        }
        return null;
    }
}
