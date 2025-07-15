# --- Stage 1: Build the Spring Boot application with Maven ---
FROM maven:3.8.7-eclipse-temurin-19-alpine AS builder

# Set working directory inside the container
WORKDIR /java-springboot

# Copy project files into the container
ADD . .

# Make the Maven wrapper executable
RUN chmod +x mvnw

# Run Maven build (skipping tests)
RUN ./mvnw clean install -DskipTests

# --- Stage 2: Create a lightweight runtime image ---
FROM openjdk:19-alpine

# Address known vulnerabilities
RUN apk add --upgrade libtasn1-progs && \
    apk update && apk upgrade zlib

# Create a non-root user for better security
RUN addgroup -g 10014 choreo && \
    adduser --disabled-password --no-create-home --uid 10014 --ingroup choreo choreouser

# Run as the non-root user
USER 10014

# Mount /tmp for use by Spring Boot
VOLUME /tmp

# Copy the built JAR file from the builder stage
COPY --from=builder /java-springboot/target/insurance-quote-service-*.jar app.jar

# Default command to run the app
CMD ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar"]
