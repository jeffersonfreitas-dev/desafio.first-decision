FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn package -P prod

FROM eclipse-temurin:17.0.9_9-jdk
RUN groupadd -r appuser && useradd -r -g appuser appuser
COPY --from=build /app/target/register-api.jar /app/register-api.jar
RUN chown appuser:appuser /app/register-api.jar
USER appuser
WORKDIR /app
EXPOSE 8080
CMD ["java", "-Duser.timezone=America/Fortaleza", "-jar", "register-api.jar"]
