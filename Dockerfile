# 빌드 스테이지: Gradle과 JDK 17을 포함한 이미지 사용
FROM gradle:7.4-jdk17 AS build

WORKDIR /app

# 프로젝트 파일을 컨테이너로 복사
COPY . .

# Gradle을 사용하여 애플리케이션 빌드
RUN ./gradlew :application:build

# 실행 스테이지: JDK 17 slim 버전 이미지 사용
FROM openjdk:17-jdk-slim

WORKDIR /app

# 빌드 스테이지에서 생성된 JAR 파일만 복사
COPY --from=build /app/application/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]