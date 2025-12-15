package com.example.sample.controller;

import com.example.sample.dto.LoginResponse;
import com.example.sample.entity.User;
import com.example.sample.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> saveMobileNumber(@RequestParam String mobileNumber) {
        User savedUser = userService.saveMobileNumber(mobileNumber);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<Object> verifyOtp(@RequestParam String mobileNumber, @RequestParam String otp) {
        User user = userService.verifyOtp(mobileNumber, otp);
        if (user != null) {
            String token = UUID.randomUUID().toString();
            LoginResponse.LoginData data = new LoginResponse.LoginData(token, user);
            LoginResponse response = new LoginResponse("Login Successful", data);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body("Invalid OTP or Mobile Number");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok("Logout Successful");
    }
}
