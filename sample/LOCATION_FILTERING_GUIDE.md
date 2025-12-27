# Location-Based Filtering - Testing Guide

## Overview

The API now automatically geocodes village names and filters listings within 50km radius.

## How It Works

1. **When creating a listing**: Village name is automatically geocoded to latitude/longitude
2. **When getting listings**: Optionally provide your location to see only nearby listings (within 50km)

---

## API Endpoints

### POST /api/listings (Create Listing)

Village name is **automatically geocoded** - no need to provide coordinates!

**Example:**
```json
{
  "title": "Tractor Available",
  "description": "Offering tractor rental",
  "category": "Agriculture",
  "villageName": "Kathmandu",
  "contactNumber": "9876543211",
  "needService": false
}
```

The system will automatically find Kathmandu's coordinates and store them.

---

### GET /api/listings (Get All Listings)

**Option 1: Get ALL listings (no filtering)**
```
GET http://localhost:8080/api/listings
```

**Option 2: Get listings within 50km of your location**
```
GET http://localhost:8080/api/listings?latitude=27.7172&longitude=85.3240
```

**Option 3: Custom radius (e.g., 100km)**
```
GET http://localhost:8080/api/listings?latitude=27.7172&longitude=85.3240&radius=100
```

---

## Testing Steps

### 1. Restart Application

The database needs to add new columns:

```powershell
# Stop current application (Ctrl+C)
cd E:\new_sample\sample
.\mvnw spring-boot:run
```

Watch for Hibernate adding columns:
```
Hibernate: alter table listings add column latitude double precision
Hibernate: alter table listings add column longitude double precision
```

### 2. Create Test Listings

**Listing 1 - Kathmandu:**
```json
POST http://localhost:8080/api/listings

{
  "title": "Tractor in Kathmandu",
  "description": "Available in Kathmandu",
  "category": "Agriculture",
  "villageName": "Kathmandu",
  "contactNumber": "9876543211",
  "needService": false
}
```

**Listing 2 - Pokhara (200km away):**
```json
POST http://localhost:8080/api/listings

{
  "title": "Tractor in Pokhara",
  "description": "Available in Pokhara",
  "category": "Agriculture",
  "villageName": "Pokhara",
  "contactNumber": "9876543212",
  "needService": false
}
```

**Listing 3 - Bhaktapur (nearby, ~10km):**
```json
POST http://localhost:8080/api/listings

{
  "title": "Tractor in Bhaktapur",
  "description": "Available in Bhaktapur",
  "category": "Agriculture",
  "villageName": "Bhaktapur",
  "contactNumber": "9876543213",
  "needService": false
}
```

### 3. Test Filtering

**From Kathmandu location (27.7172, 85.3240):**
```
GET http://localhost:8080/api/listings?latitude=27.7172&longitude=85.3240&radius=50
```

**Expected Result:**
- ✅ Should return Kathmandu listing
- ✅ Should return Bhaktapur listing (~10km away)
- ❌ Should NOT return Pokhara listing (~200km away)

### 4. Test Without Location

```
GET http://localhost:8080/api/listings
```

**Expected Result:**
- ✅ Should return ALL listings (backward compatible)

---

## Response Format

Listings now include coordinates:

```json
{
  "data": [
    {
      "id": 1,
      "title": "Tractor in Kathmandu",
      "description": "Available in Kathmandu",
      "category": "Agriculture",
      "villageName": "Kathmandu",
      "contactNumber": "9876543211",
      "status": "OFFER",
      "needService": false,
      "latitude": 27.7172,
      "longitude": 85.3240,
      "date": "2025-12-27"
    }
  ]
}
```

---

## Common Coordinates for Testing

| Location | Latitude | Longitude |
|----------|----------|-----------|
| Kathmandu | 27.7172 | 85.3240 |
| Pokhara | 28.2096 | 83.9856 |
| Bhaktapur | 27.6710 | 85.4298 |
| Lalitpur | 27.6667 | 85.3167 |
| Chitwan | 27.5291 | 84.3542 |

---

## Troubleshooting

**If geocoding fails:**
- The listing will still be created but without coordinates
- It won't appear in location-filtered results
- Check application logs for geocoding errors

**If all listings appear in filtered results:**
- Make sure you're passing latitude and longitude parameters
- Check that listings have coordinates in the response

---

## Notes

- Geocoding uses OpenStreetMap Nominatim API (free, no API key needed)
- Default search radius is 50km
- Listings without coordinates are excluded from location-based filtering
- Backward compatible: works without location parameters
