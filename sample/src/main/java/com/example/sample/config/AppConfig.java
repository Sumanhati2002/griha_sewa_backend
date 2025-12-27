package com.example.sample.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Application configuration class for environment-specific properties
 */
@Configuration
public class AppConfig {

    @Value("${app.base-url}")
    private String baseUrl;

    /**
     * Get the base URL for the application based on the active profile
     * 
     * @return Base URL (e.g., http://localhost:8080 for dev,
     *         http://52.65.191.23:8080 for prod)
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * Get the API base URL
     * 
     * @return API base URL with /api suffix
     */
    public String getApiBaseUrl() {
        return baseUrl + "/api";
    }
}
