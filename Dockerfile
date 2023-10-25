FROM eclipse-temurin:17

LABEL maintainer="sergienkoyura5@gmail.com"

WORKDIR /app

COPY build/libs/carpooling-0.0.1-SNAPSHOT.jar /app/application.jar

ENTRYPOINT ["java", "-jar", "application.jar"]