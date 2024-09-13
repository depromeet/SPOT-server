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
#ENTRYPOINT ["java", "-jar", "app.jar"]

# JVM 튜닝 옵션 추가
ENTRYPOINT ["java", \
            "-Xms2048m", \
            "-Xmx2048m", \
            "-Xminf0.4", \
            "-Xmaxf0.7", \
            "-jar", "app.jar"]