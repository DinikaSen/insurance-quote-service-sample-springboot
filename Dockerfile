# ---- Stage 1: Build with Maven ----
FROM maven:3.8.7-eclipse-temurin-19-alpine AS builder

# Set working directory
WORKDIR /java-springboot

# Copy everything into the container
COPY . .

# Build the project without running tests
RUN mvn clean install -DskipTests

# ---- Stage 2: Run Spring Boot app with JDK ----
FROM openjdk:19-alpine

# Security updates (optional but good practice)
RUN apk add --no-cache libtasn1-progs zlib

# Create a non-root user
RUN addgroup -g 10014 choreo && \
    adduser --disabled-password --no-create-home --uid 10014 --ingroup choreo choreouser

USER choreouser
VOLUME /tmp

# Copy the built JAR from the builder stage
COPY --from=builder /java-springboot/target/insurance-quote-service-*.jar /app.jar

# Set default command to run the application
CMD ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar"]
