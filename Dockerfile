# ============================================================
# Stage 1 — Build the jar
# ============================================================
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /build

# Copy only pom first — lets Docker cache the dependency download
COPY pom.xml .
RUN mvn -B -q dependency:go-offline

# Now copy source and build
COPY src ./src
RUN mvn -B -q clean package -DskipTests

# ============================================================
# Stage 2 — Runtime (JRE only)
# ============================================================
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Non-root user for security
RUN addgroup -S app && adduser -S app -G app
USER app

# Copy the built jar
COPY --from=build --chown=app:app /build/target/*.jar app.jar

# Expose app port
EXPOSE 8080

# Health check — hits actuator (see notes below) or the API root
HEALTHCHECK --interval=30s --timeout=5s --start-period=40s --retries=3 \
    CMD wget -qO- http://localhost:8080/v3/api-docs >/dev/null 2>&1 || exit 1

# JVM opts for containers — sensible defaults
ENV JAVA_OPTS="-XX:MaxRAMPercentage=75.0 -XX:+UseG1GC -Djava.security.egd=file:/dev/./urandom"

# Run the jar
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar app.jar"]