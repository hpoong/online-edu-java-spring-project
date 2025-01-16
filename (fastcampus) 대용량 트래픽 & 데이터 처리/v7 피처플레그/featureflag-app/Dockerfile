FROM openjdk:17-jdk-slim
ARG JAR_FILE=build/libs/featureflag-app.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
