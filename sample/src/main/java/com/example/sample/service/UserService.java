package com.example.sample.service;

import com.example.sample.entity.User;

public interface UserService {
    User register(String mobileNumber, String name);

    User login(String mobileNumber);

    User getUserByMobileNumber(String mobileNumber);
}
