package com.example.sample.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;

@Service
public class GeocodingService {

    private final RestTemplate restTemplate;

    public GeocodingService() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * Geocode a village name to latitude/longitude using Nominatim API
     * 
     * @param villageName Name of the village
     * @return Array with [latitude, longitude] or null if not found
     */
    public Double[] geocodeVillage(String villageName) {
        try {
            String url = String.format(
                    "https://nominatim.openstreetmap.org/search?q=%s,Nepal&format=json&limit=1",
                    villageName.replace(" ", "+"));

            // Set User-Agent header (required by Nominatim)
            org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
            headers.set("User-Agent", "VillageConnect/1.0");

            org.springframework.http.HttpEntity<String> entity = new org.springframework.http.HttpEntity<>(headers);

            org.springframework.http.ResponseEntity<JsonNode[]> response = restTemplate.exchange(
                    url,
                    org.springframework.http.HttpMethod.GET,
                    entity,
                    JsonNode[].class);

            JsonNode[] results = response.getBody();

            if (results != null && results.length > 0) {
                JsonNode firstResult = results[0];
                double lat = firstResult.get("lat").asDouble();
                double lon = firstResult.get("lon").asDouble();
                return new Double[] { lat, lon };
            }

            return null;
        } catch (Exception e) {
            System.err.println("Geocoding failed for: " + villageName + " - " + e.getMessage());
            return null;
        }
    }
}
