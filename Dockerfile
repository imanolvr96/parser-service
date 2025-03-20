# Usa una imagen base de Java 23 con JDK para la compilación
FROM eclipse-temurin:23-jdk AS build

# Establece el directorio de trabajo
WORKDIR /app

# Copia los archivos del proyecto
COPY . .

# Compila la aplicación (asume que usas Maven Wrapper)
RUN ./mvnw clean package -DskipTests

# Segunda etapa: imagen más ligera con solo el JRE
FROM eclipse-temurin:23-jre

# Establece el directorio de trabajo
WORKDIR /app

# Copia el JAR desde la etapa de construcción
COPY --from=build /app/target/*.jar app.jar

# Expone el puerto de la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "app.jar"]