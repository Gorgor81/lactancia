# Imagen base con Maven para compilar el proyecto
FROM maven:3.8.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copiar los archivos del proyecto y compilar
COPY pom.xml .
RUN mvn dependency:go-offline  # Descarga dependencias para evitar fallos de red
COPY src ./src
RUN mvn clean package -DskipTests  # Construye el JAR

# Imagen final con Java para ejecutar el JAR
FROM eclipse-temurin:17
WORKDIR /app

# Copiar el JAR desde la imagen de construcción
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto en el que corre Spring Boot
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app.jar"]
