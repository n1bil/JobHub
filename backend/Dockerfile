# openjdk:19-jdk-alpine
# VOLUME /tmp
# COPY target/*.jar app.jar
# ENTRYPOINT ["java","-jar","/app.jar"]
# EXPOSE 8080

FROM openjdk:19-jdk-alpine
WORKDIR /app
COPY /target/backend-1.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]