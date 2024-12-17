# Etapa 1: Build
FROM eclipse-temurin:21 AS build

RUN apt-get update && apt-get install -y maven
WORKDIR /app
COPY pom.xml ./
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21

WORKDIR /app
COPY --from=build /app/target/SpringBootCleanarchApplication-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
