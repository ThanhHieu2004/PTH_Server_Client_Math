# syntax=docker/dockerfile:1

################################################################################
# Stage 1: Download Dependencies
FROM eclipse-temurin:21-jdk-jammy as deps

WORKDIR /build

# Copy the mvnw wrapper and set permissions
COPY mvnw mvnw
RUN chmod +x mvnw

# Copy Maven settings and project files
COPY .mvn/ .mvn/
COPY pom.xml pom.xml

# Cache dependencies
RUN --mount=type=cache,id=s/a9aa0018-ac34-43fc-bee4-e64535636d0d-/root/.m2,target=/root/.m2 ./mvnw dependency:go-offline -DskipTests

################################################################################
# Stage 2: Build Application
FROM deps as package

WORKDIR /build

# Copy source code
COPY src src/

# Build application
RUN --mount=type=cache,id=s/a9aa0018-ac34-43fc-bee4-e64535636d0d-/root/.m2,target=/root/.m2 \
    ./mvnw package -DskipTests && \
    mv target/*.jar target/app.jar

################################################################################
# Stage 3: Run Application
FROM eclipse-temurin:21-jre-jammy AS final

# Install ngrok
RUN apt update && apt install -y curl && \
    curl -s https://ngrok-agent.s3.amazonaws.com/ngrok.asc | tee /etc/apt/trusted.gpg.d/ngrok.asc && \
    echo "deb https://ngrok-agent.s3.amazonaws.com buster main" | tee /etc/apt/sources.list.d/ngrok.list && \
    apt update && apt install -y ngrok

# Create a non-privileged user
ARG UID=10001
RUN adduser \
    --disabled-password \
    --gecos "" \
    --home "/nonexistent" \
    --shell "/sbin/nologin" \
    --no-create-home \
    --uid "${UID}" \
    appuser
USER appuser

# Copy built JAR file
COPY --from=package /build/target/app.jar app.jar

# Expose the application port
EXPOSE 2025

# Run the application
ENTRYPOINT [ "java", "-jar", "app.jar" ]
