# SCM Service

A simple REST service that provides information about Source Code Management (SCM) systems used by different assets.

## Overview

This service allows you to:
- Retrieve SCM information for a specific asset code
- Add new SCM information for assets

The service stores information about which SCM system (Git, SVN, Mercurial, etc.) is used by each asset, along with the repository URL.

## Technologies

- Java 21
- Spring Boot 3.4.0
- Maven
- Docker

## Getting Started

### Prerequisites

- Java 21 or higher
- Maven 3.6 or higher
- Docker (optional, for containerized deployment)

### Running Locally

1. Clone the repository:
   ```
   git clone https://github.com/yourusername/scm-service.git
   cd scm-service
   ```

2. Build the application:
   ```
   ./mvnw clean package
   ```

3. Run the application:
   ```
   ./mvnw spring-boot:run
   ```

4. The service will be available at http://localhost:8080

### Running with Docker

1. Build the Docker image:
   ```
   docker build -t scm-service .
   ```

2. Run the container:
   ```
   docker run -p 8080:8080 scm-service
   ```

3. Alternatively, use Docker Compose:
   ```
   docker compose up
   ```

## API Documentation

### Get SCM Information

**Endpoint:** `GET /api/scm/{assetCode}`

**Example Request:**
```bash
curl -X GET http://localhost:8080/api/scm/APP001
```

**Example Response:**
```json
{
  "assetCode": "APP001",
  "scmType": "Git",
  "repositoryUrl": "https://github.com/org/app001"
}
```

### Add SCM Information

**Endpoint:** `POST /api/scm`

**Example Request:**
```bash
curl -X POST http://localhost:8080/api/scm \
  -H "Content-Type: application/json" \
  -d '{"assetCode":"APP004","scmType":"Git","repositoryUrl":"https://github.com/org/app004"}'
```

**Example Response:**
```json
{
  "assetCode": "APP004",
  "scmType": "Git",
  "repositoryUrl": "https://github.com/org/app004"
}
```

## Testing

Run the tests with:
```
./mvnw test
```

The project includes:
- Unit tests for service and controller layers
- Integration tests for the API endpoints

## Project Structure

```
scm-service/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/scmservice/
│   │   │       ├── controller/
│   │   │       ├── model/
│   │   │       ├── service/
│   │   │       └── ScmServiceApplication.java
│   │   └── resources/
│   └── test/
│       └── java/
│           └── com/example/scmservice/
│               ├── controller/
│               ├── integration/
│               ├── model/
│               ├── service/
│               └── ScmServiceApplicationTests.java
├── .mvn/
├── Dockerfile
├── compose.yaml
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

## License

This project is licensed under the MIT License - see the LICENSE file for details.
