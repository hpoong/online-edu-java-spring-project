FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY build/libs/search-service.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]