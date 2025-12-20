package com.example.sample.service_impl;

import com.example.sample.entity.User;
import com.example.sample.repository.UserRepository;
import com.example.sample.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User register(String mobileNumber, String name) {
        Optional<User> existingUser = userRepository.findByMobileNumber(mobileNumber);
        if (existingUser.isPresent()) {
            throw new RuntimeException("User with this mobile number already exists");
        }
        User newUser = new User();
        newUser.setMobileNumber(mobileNumber);
        newUser.setName(name);

        return userRepository.save(newUser);
    }

    @Override
    public User login(String mobileNumber) {
        return userRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User getUserByMobileNumber(String mobileNumber) {
        return userRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
