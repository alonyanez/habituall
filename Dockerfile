# Etapa 1: Construcción (Build)
FROM maven:3.8.4-openjdk-17-slim AS build
COPY . /app
WORKDIR /app
RUN mvn clean package -DskipTests

# Etapa 2: Ejecución (Run)
FROM openjdk:17-jdk-slim
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
# Limitamos la memoria para que quepa en el plan gratuito de Render
ENTRYPOINT ["java", "-Xmx380m", "-Xss256k", "-jar", "/app.jar"]