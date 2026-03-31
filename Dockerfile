# Etapa 1: Construcción (Maven + Temurin 17)
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build
COPY . /app
WORKDIR /app
RUN mvn clean package -DskipTests

# Etapa 2: Ejecución (JRE ligero para ahorrar RAM)
FROM eclipse-temurin:17-jre-alpine
COPY --from=build /app/target/*.jar app.jar

# Configuración de memoria optimizada para los 512MB de Render
ENV JAVA_OPTS="-Xmx300m -Xms200m -XX:+UseSerialGC"
EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app.jar"]