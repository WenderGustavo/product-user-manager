# Etapa 1: Build
FROM eclipse-temurin:21 AS build

# Instale o Maven para buildar o projeto
RUN apt-get update && apt-get install -y maven

# Configure o diretório de trabalho
WORKDIR /app

# Copie o arquivo pom.xml e as dependências
COPY pom.xml ./
RUN mvn dependency:resolve

# Copie o código-fonte e compile o projeto
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Runtime
FROM eclipse-temurin:21

# Configure o diretório de trabalho
WORKDIR /app

# Copie o arquivo .jar gerado
COPY --from=build /app/target/SpringBootCleanarchApplication-0.0.1-SNAPSHOT.jar app.jar

# Exponha a porta do container
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
