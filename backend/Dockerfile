FROM alpine:latest
RUN apk --no-cache add openjdk19
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080