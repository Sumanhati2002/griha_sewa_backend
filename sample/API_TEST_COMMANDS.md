# cURL Commands for Testing Listings API

## Prerequisites

Make sure your application is running:
```powershell
cd E:\new_sample\sample
.\mvnw spring-boot:run
```

---

## 1. Create Listing WITH New Fields

### Test with mobileNumber and serviceDate

```bash
curl -X POST http://localhost:8080/api/listings \
  -H "Content-Type: application/json" \
  -d "{\"title\":\"Agriculture Service Needed\",\"description\":\"Need tractor for plowing\",\"category\":\"Agriculture\",\"villageName\":\"Rampur\",\"contactNumber\":\"9876543211\",\"needService\":true,\"mobileNumber\":\"9999888877\",\"serviceDate\":\"2025-01-15\"}"
```

**PowerShell Version:**
```powershell
curl.exe -X POST http://localhost:8080/api/listings `
  -H "Content-Type: application/json" `
  -d '{\"title\":\"Agriculture Service Needed\",\"description\":\"Need tractor for plowing\",\"category\":\"Agriculture\",\"villageName\":\"Rampur\",\"contactNumber\":\"9876543211\",\"needService\":true,\"mobileNumber\":\"9999888877\",\"serviceDate\":\"2025-01-15\"}'
```

**Expected Response:**
```json
{
  "id": 1,
  "title": "Agriculture Service Needed",
  "description": "Need tractor for plowing",
  "category": "Agriculture",
  "villageName": "Rampur",
  "contactNumber": "9876543211",
  "status": "NEED",
  "needService": true,
  "mobileNumber": "9999888877",
  "serviceDate": "2025-01-15",
  "date": "2025-12-27",
  "userId": null
}
```

---

## 2. Create Listing WITHOUT New Fields (Backward Compatibility)

### Test that old format still works

```bash
curl -X POST http://localhost:8080/api/listings \
  -H "Content-Type: application/json" \
  -d "{\"title\":\"Offering Tutoring\",\"description\":\"Math and Science tutoring available\",\"category\":\"Education\",\"villageName\":\"Lakshmipur\",\"contactNumber\":\"1234567890\",\"needService\":false}"
```

**PowerShell Version:**
```powershell
curl.exe -X POST http://localhost:8080/api/listings `
  -H "Content-Type: application/json" `
  -d '{\"title\":\"Offering Tutoring\",\"description\":\"Math and Science tutoring available\",\"category\":\"Education\",\"villageName\":\"Lakshmipur\",\"contactNumber\":\"1234567890\",\"needService\":false}'
```

**Expected Response:**
```json
{
  "id": 2,
  "title": "Offering Tutoring",
  "description": "Math and Science tutoring available",
  "category": "Education",
  "villageName": "Lakshmipur",
  "contactNumber": "1234567890",
  "status": "OFFER",
  "needService": false,
  "mobileNumber": null,
  "serviceDate": null,
  "date": "2025-12-27",
  "userId": null
}
```

---

## 3. Get All Listings

```bash
curl -X GET http://localhost:8080/api/listings
```

**PowerShell Version:**
```powershell
curl.exe -X GET http://localhost:8080/api/listings
```

**Expected Response:**
```json
[
  {
    "id": 1,
    "title": "Agriculture Service Needed",
    "description": "Need tractor for plowing",
    "category": "Agriculture",
    "villageName": "Rampur",
    "contactNumber": "9876543211",
    "status": "NEED",
    "needService": true,
    "mobileNumber": "9999888877",
    "serviceDate": "2025-01-15",
    "date": "2025-12-27",
    "userId": null
  },
  {
    "id": 2,
    "title": "Offering Tutoring",
    "description": "Math and Science tutoring available",
    "category": "Education",
    "villageName": "Lakshmipur",
    "contactNumber": "1234567890",
    "status": "OFFER",
    "needService": false,
    "mobileNumber": null,
    "serviceDate": null,
    "date": "2025-12-27",
    "userId": null
  }
]
```

---

## 4. Additional Test Cases

### Create OFFER with new fields

```bash
curl -X POST http://localhost:8080/api/listings \
  -H "Content-Type: application/json" \
  -d "{\"title\":\"Plumber Available\",\"description\":\"Experienced plumber offering services\",\"category\":\"Services\",\"villageName\":\"Greenfield\",\"contactNumber\":\"5555444433\",\"needService\":false,\"mobileNumber\":\"6666777788\",\"serviceDate\":\"2025-02-01\"}"
```

**PowerShell Version:**
```powershell
curl.exe -X POST http://localhost:8080/api/listings `
  -H "Content-Type: application/json" `
  -d '{\"title\":\"Plumber Available\",\"description\":\"Experienced plumber offering services\",\"category\":\"Services\",\"villageName\":\"Greenfield\",\"contactNumber\":\"5555444433\",\"needService\":false,\"mobileNumber\":\"6666777788\",\"serviceDate\":\"2025-02-01\"}'
```

### Create with only mobileNumber (no serviceDate)

```bash
curl -X POST http://localhost:8080/api/listings \
  -H "Content-Type: application/json" \
  -d "{\"title\":\"Electrician Needed\",\"description\":\"Need electrician for house wiring\",\"category\":\"Services\",\"villageName\":\"Sundarpur\",\"contactNumber\":\"7777888899\",\"needService\":true,\"mobileNumber\":\"8888999900\"}"
```

**PowerShell Version:**
```powershell
curl.exe -X POST http://localhost:8080/api/listings `
  -H "Content-Type: application/json" `
  -d '{\"title\":\"Electrician Needed\",\"description\":\"Need electrician for house wiring\",\"category\":\"Services\",\"villageName\":\"Sundarpur\",\"contactNumber\":\"7777888899\",\"needService\":true,\"mobileNumber\":\"8888999900\"}'
```

---

## 5. Pretty Print JSON (Optional)

If you want formatted JSON output, you can pipe to `jq` (if installed):

```bash
curl -X GET http://localhost:8080/api/listings | jq
```

Or in PowerShell:
```powershell
curl.exe -X GET http://localhost:8080/api/listings | ConvertFrom-Json | ConvertTo-Json -Depth 10
```

---

## Quick Test Summary

1. ✅ **Test 1**: Create listing with both new fields
2. ✅ **Test 2**: Create listing without new fields (backward compatibility)
3. ✅ **Test 3**: Get all listings and verify both formats work
4. ✅ **Test 4**: Create OFFER with new fields
5. ✅ **Test 5**: Create with partial new fields

All tests should pass without errors!
