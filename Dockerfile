# syntax=docker/dockerfile:1

# ---- Build stage ----
# Full JDK image: compiles the application and packages the executable JAR.
FROM eclipse-temurin:23-jdk AS build
WORKDIR /app

# Copy the Maven wrapper and the POM first, then resolve dependencies.
# Doing this before copying source means Docker caches the dependency layer,
# so changing a source file does not re-download the whole dependency tree.
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline -B

# Copy the source and build. Tests are skipped here on purpose: the
# repository and context tests need a live database, which is not present
# during an image build. The GitHub Actions pipeline runs the full suite
# against a real Postgres service container instead.
COPY src/ src/
RUN ./mvnw clean package -DskipTests -B

# ---- Run stage ----
# Slim JRE image: carries only the runtime and the built JAR, not Maven
# or the JDK, which keeps the final image small.
FROM eclipse-temurin:23-jre AS run
WORKDIR /app

# Run as a non-root user. Containers that run as root are a common and
# easily avoided security smell.
RUN groupadd --system spring && useradd --system --gid spring spring
USER spring:spring

# Copy just the packaged JAR from the build stage.
COPY --from=build /app/target/travel-recommendation-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]