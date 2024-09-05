# openjdk:19-jdk-alpine
# VOLUME /tmp
# COPY target/*.jar app.jar
# ENTRYPOINT ["java","-jar","/app.jar"]
# EXPOSE 8080

# Этап 1: Сборка приложения
FROM openjdk:19-jdk-alpine AS build
WORKDIR /app
COPY . /app
RUN ./mvnw clean package -DskipTests

# Этап 2: Создание финального образа
FROM openjdk:19-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/backend-1.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]


