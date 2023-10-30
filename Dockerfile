FROM eclipse-temurin:17

LABEL maintainer="sergienkoyura5@gmail.com"

WORKDIR /app

COPY build/libs/*.jar /app/application.jar

ENTRYPOINT ["java", "-jar", "application.jar"]