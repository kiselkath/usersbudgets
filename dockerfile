# Stage 1: Build the application
FROM eclipse-temurin:23-jdk-jammy as builder
WORKDIR /app

# Кэшируем зависимости
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw && ./mvnw -q dependency:go-offline

# Сборка приложения
COPY src ./src
RUN ./mvnw -q package -DskipTests

# Stage 2: Create the final, smaller image
FROM eclipse-temurin:23-jre-jammy
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]