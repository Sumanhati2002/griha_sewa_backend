# Environment Configuration Guide

This application supports multiple environments (development and production) with different configurations.

## Available Profiles

### 1. Development Profile (`dev`)
- **Base URL**: `http://localhost:8080`
- **API Endpoint**: `http://localhost:8080/api/listings`
- **Database**: Local PostgreSQL (default: localhost:5432)
- **Logging**: DEBUG level
- **SQL Logging**: Enabled
- **DDL Auto**: `update` (automatically updates database schema)

### 2. Production Profile (`prod`)
- **Base URL**: `http://52.65.191.23:8080`
- **API Endpoint**: `http://52.65.191.23:8080/api/listings`
- **Database**: Production PostgreSQL (configured via environment variables)
- **Logging**: INFO level
- **SQL Logging**: Disabled
- **DDL Auto**: `validate` (only validates schema, doesn't modify)

## Running the Application

### Development Environment

**Option 1: Using Maven Wrapper (Recommended)**
```bash
.\mvnw spring-boot:run
```

**Option 2: With explicit profile**
```bash
.\mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

**Option 3: Using environment variable**
```bash
$env:SPRING_PROFILES_ACTIVE="dev"
.\mvnw spring-boot:run
```

### Production Environment

**Option 1: Using Maven Wrapper**
```bash
.\mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

**Option 2: Using environment variable**
```bash
$env:SPRING_PROFILES_ACTIVE="prod"
.\mvnw spring-boot:run
```

**Option 3: Running JAR file**
```bash
# Build the JAR
.\mvnw clean package -DskipTests

# Run with production profile
java -jar -Dspring.profiles.active=prod target/sample-0.0.1-SNAPSHOT.jar
```

## Environment Variables

### Development (Optional - defaults provided)
```bash
$env:DB_NAME="griha_sewa"
$env:DB_USERNAME="postgres"
$env:DB_PASSWORD="postgres"
$env:JWT_SECRET="your_very_secure_secret_key_which_should_be_long_enough_at_least_32_bytes"
```

### Production (Required)
```bash
$env:DB_HOST="your-db-host"
$env:DB_PORT="5432"
$env:DB_NAME="your-db-name"
$env:DB_USERNAME="your-db-username"
$env:DB_PASSWORD="your-db-password"
$env:JWT_SECRET="your-production-secret-key"
```

## Using Base URL in Code

The base URL is available through the `AppConfig` class:

```java
@Autowired
private AppConfig appConfig;

public void someMethod() {
    String baseUrl = appConfig.getBaseUrl();
    // Returns: http://localhost:8080 (dev) or http://52.65.191.23:8080 (prod)
    
    String apiBaseUrl = appConfig.getApiBaseUrl();
    // Returns: http://localhost:8080/api (dev) or http://52.65.191.23:8080/api (prod)
}
```

## Configuration Files

- **application.properties** - Main configuration, sets default profile to `dev`
- **application-dev.properties** - Development-specific settings
- **application-prod.properties** - Production-specific settings

## Testing

The test environment uses an in-memory H2 database configured in `src/test/resources/application.properties`.

```bash
# Run tests
.\mvnw test

# Run tests with specific profile
.\mvnw test -Dspring.profiles.active=dev
```

## Deployment Checklist

Before deploying to production:

1. ✅ Set `SPRING_PROFILES_ACTIVE=prod` environment variable
2. ✅ Configure all required database environment variables
3. ✅ Set a strong `JWT_SECRET` (minimum 32 characters)
4. ✅ Verify database connectivity
5. ✅ Run database migrations if needed
6. ✅ Build the application: `.\mvnw clean package`
7. ✅ Test the production build locally before deployment

## Troubleshooting

### Issue: Application uses wrong profile
**Solution**: Check that `SPRING_PROFILES_ACTIVE` environment variable is set correctly

### Issue: Database connection fails
**Solution**: Verify all database environment variables are set and the database is accessible

### Issue: JWT token errors
**Solution**: Ensure `JWT_SECRET` is set and is at least 32 characters long

### Issue: Application starts on wrong port
**Solution**: Check `server.port` in the active profile's properties file
