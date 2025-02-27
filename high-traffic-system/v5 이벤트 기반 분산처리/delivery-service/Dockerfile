FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY build/libs/delivery-service.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]