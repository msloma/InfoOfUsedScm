# SCM Service

A simple REST service that provides information about Source Code Management (SCM) systems used by different assets.

## Overview

This service allows you to:
- Retrieve SCM information for a specific asset code
- Add new SCM information for assets

The service stores information about which SCM system (Git, SVN, Mercurial, etc.) is used by each asset, along with the repository URL. It uses Redis for distributed caching and session management, making it scalable in a Kubernetes environment.

## Technologies

- Java 21
- Spring Boot 3.4.0
- Spring Data Redis
- Spring Session
- Maven
- Docker
- Kubernetes
- Helm

## Getting Started

### Prerequisites

- Java 21 or higher
- Maven 3.6 or higher
- Docker and Docker Compose (for containerized deployment)
- Kubernetes cluster (for Kubernetes deployment)
- Helm 3.x (for Helm chart deployment)

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

### Running with Docker Compose

1. Build and start the services:
   ```
   docker compose up -d
   ```

2. The service will be available at http://localhost:8080

### Deploying with Helm

1. Build and push the Docker image to your registry:
   ```
   docker build -t your-registry/scm-service:latest .
   docker push your-registry/scm-service:latest
   ```

2. Update the image repository in `helm/scm-service/values.yaml`

3. Install the Helm chart:
   ```
   helm install scm-service ./helm/scm-service
   ```

4. To upgrade an existing release:
   ```
   helm upgrade scm-service ./helm/scm-service
   ```

5. To uninstall the release:
   ```
   helm uninstall scm-service
   ```

### Customizing the Helm Deployment

You can customize the deployment by overriding values in the `values.yaml` file:

```
helm install scm-service ./helm/scm-service --set replicaCount=5,image.tag=v1.0.0
```

Or by providing a custom values file:

```
helm install scm-service ./helm/scm-service -f custom-values.yaml
```

## Scaling

The application is designed to be scalable in a Kubernetes environment:

- Redis is used for distributed caching and session management
- Health checks are configured for proper pod lifecycle management
- Resource limits are defined to ensure efficient resource utilization
- Horizontal Pod Autoscaler is configured for automatic scaling

To manually scale the application:

```
kubectl scale deployment scm-service --replicas=5
```

Or when using Helm:

```
helm upgrade scm-service ./helm/scm-service --set replicaCount=5
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

**Error Response (Asset Not Found):**
```json
{
  "timestamp": "2024-07-01T12:34:56.789",
  "status": 404,
  "error": "Not Found",
  "message": "Asset not found with assetCode: 'NONEXISTENT'",
  "path": "/api/scm/NONEXISTENT"
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

Note: This project uses Spring Boot 3.4.0, which introduces `@MockitoBean` as a replacement for the deprecated `@MockBean` annotation.

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
