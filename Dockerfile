# syntax=docker/dockerfile:1

# Stage 1: Build the application
#FROM eclipse-temurin:17-jdk-jammy as build



# Copy the Maven wrapper and POM
#COPY mvnw .
#COPY .mvn .mvn
#COPY pom.xml .

# Make the Maven wrapper executable
#RUN chmod +x mvnw

# Download dependencies (leveraging Docker cache)
#RUN ./mvnw dependency:go-offline -B

# Copy source code
#COPY src src

# Build the application
#RUN ./mvnw package -DskipTests
#RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

# Stage 2: Create the runtime image
FROM eclipse-temurin:17-jre-ubi9-minimal

WORKDIR /app

ARG TARGET=/app/target/

# Copy binary
COPY target ${TARGET}

ENV DEPENDENCY=${TARGET}/dependency

# Create a non-root user
RUN addgroup --system --gid 1001 appuser && \
    adduser --system --uid 1001 --gid 1001 appuser

# Copy application dependencies
#COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
#COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
#COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

# Set the user to run the application
USER appuser

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-cp", "/app:/app/lib/*", "com.example.scmservice.ScmServiceApplication"]