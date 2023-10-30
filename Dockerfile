# Use the official Gradle image as the build environment
FROM gradle:7.3-jdk17 as build

# Set working directory
WORKDIR /app

# Copy your source code and build files to the image
COPY . .

# Use Gradle to build the application
RUN gradle clean build

# Use the Eclipse Temurin 17 image as the runtime environment
FROM eclipse-temurin:17

LABEL maintainer="sergienkoyura5@gmail.com"

WORKDIR /app

# Copy the built JAR file from the build image to the current image
COPY --from=build /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]