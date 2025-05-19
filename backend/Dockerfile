# Usa una imagen con Maven para construir el proyecto
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia los archivos necesarios para construir el proyecto
COPY pom.xml .
COPY src ./src

# Compila el proyecto (crea el JAR)
RUN mvn clean package -DskipTests

# Imagen final: solo con Java y el JAR
FROM eclipse-temurin:17-jdk-alpine

# Directorio de la aplicación
WORKDIR /app

# Copia el JAR desde la etapa de construcción
COPY --from=build /app/target/TicketFlowBack-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto por defecto de Spring Boot
EXPOSE 8080

# Comando de inicio
ENTRYPOINT ["java", "-jar", "app.jar"]