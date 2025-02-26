# Etapa 1: Compilar el proyecto
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copiar archivos y compilar
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Ejecutar la aplicación
FROM eclipse-temurin:17
WORKDIR /app

# Copiar el JAR desde la etapa de compilación
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto de la app
EXPOSE 8080

# Ejecutar la aplicación
CMD ["java", "-jar", "app.jar"]
