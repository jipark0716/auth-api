FROM openjdk:21 AS build-env
COPY ./ ./
RUN ./gradlew bootJar

FROM openjdk:21
WORKDIR /App
COPY --from=build-env build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
