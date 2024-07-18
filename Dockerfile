# 빌드 스테이지
FROM gradle:7.4-jdk17 AS build
WORKDIR /app
COPY . .
RUN ./gradlew build -x test

# 실행 스테이지
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/application/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]