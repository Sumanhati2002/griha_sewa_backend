# Quick Start Guide

## ✅ Tests Passing!
Your application is now properly configured for multiple environments.

## Running the Application

### 1. Development Mode (Default)

```powershell
# Navigate to project directory
cd E:\new_sample\sample

# Run in development mode
.\mvnw spring-boot:run
```

**Development URLs:**
- Base: `http://localhost:8080`
- API: `http://localhost:8080/api/listings`

### 2. Production Mode

```powershell
# Navigate to project directory
cd E:\new_sample\sample

# Set production profile and environment variables
$env:SPRING_PROFILES_ACTIVE='prod'
$env:DB_HOST='52.65.191.23'  # Your production database host
$env:DB_PORT='5432'
$env:DB_NAME='village_connect'
$env:DB_USERNAME='your_username'
$env:DB_PASSWORD='your_password'
$env:JWT_SECRET='your_production_secret_minimum_32_characters'

# Run the application
.\mvnw spring-boot:run
```

**Production URLs:**
- Base: `http://52.65.191.23:8080`
- API: `http://52.65.191.23:8080/api/listings`

### 3. Running Tests

```powershell
cd E:\new_sample\sample
.\mvnw test
```

Tests use H2 in-memory database automatically.

## Environment Profiles

| Profile | Base URL | Database | Logging |
|---------|----------|----------|---------|
| **dev** (default) | `localhost:8080` | Local PostgreSQL | DEBUG |
| **prod** | `52.65.191.23:8080` | Production PostgreSQL | INFO |
| **test** | `localhost:8080` | H2 In-Memory | DEBUG |

## Common Issues

### "mvnw not found"
**Solution**: Make sure you're in `E:\new_sample\sample\` directory, not `E:\new_sample\`

### Database connection errors
**Solution**: 
- For **dev**: Ensure PostgreSQL is running locally with database `village_connect`
- For **prod**: Set all required environment variables (`DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USERNAME`, `DB_PASSWORD`)

### Test failures
**Solution**: Tests now use H2 database and should pass automatically

## Files Created

- `application.properties` - Main config (sets default profile to dev)
- `application-dev.properties` - Development configuration
- `application-prod.properties` - Production configuration
- `application-test.properties` - Test configuration (H2 database)
- `src/test/resources/application.properties` - Legacy test config (can be deleted)
- `AppConfig.java` - Configuration class for accessing base URLs in code
- `SampleApplicationTests.java` - Updated with `@ActiveProfiles("test")` annotation

## Next Steps

1. **Start development**: `.\mvnw spring-boot:run`
2. **Access API**: `http://localhost:8080/api/listings`
3. **For production**: Set environment variables and use `prod` profile

For detailed documentation, see [ENVIRONMENT_SETUP.md](file:///e:/new_sample/sample/ENVIRONMENT_SETUP.md)
