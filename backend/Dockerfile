# Этап 1: Сборка приложения
FROM openjdk:19-jdk-alpine AS build
WORKDIR /app

# Устанавливаем dos2unix
RUN apk add --no-cache dos2unix

# Копируем файлы для установки зависимостей
COPY mvnw ./
COPY .mvn .mvn
COPY pom.xml ./

# Конвертируем окончания строк и делаем скрипт исполняемым
RUN dos2unix mvnw && chmod +x mvnw

# Устанавливаем зависимости
RUN ./mvnw dependency:go-offline -B

# Копируем остальные файлы проекта
COPY src ./src

# Сборка приложения
RUN ./mvnw clean package -DskipTests

# Этап 2: Создание финального образа
FROM openjdk:19-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar /app/app.jar

# Запуск приложения
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
EXPOSE 8081
