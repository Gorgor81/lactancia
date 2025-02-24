# Imagen base con Java 17 (ajusta si usas otra versión)
FROM eclipse-temurin:17

# Establecer directorio de trabajo
WORKDIR /app

# Copiar el archivo JAR generado
COPY target/*.jar app.jar

# Exponer el puerto en el que corre Spring Boot
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app.jar"]
