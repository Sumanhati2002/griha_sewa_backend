package com.example.sample.dto;

import com.example.sample.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String message;
    private LoginData data;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginData {
        private String token;
        private User user;
    }
}
