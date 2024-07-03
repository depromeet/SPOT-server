# dockerfile 실행 전 server wiki를 참고하여 명령어를 사용해주세요.
# spring boot 애플리케이션이 빌드되어야 실행 가능해요!

# ubuntu 20.04 버전을 기반으로 합니다.
FROM ubuntu:20.04

# RUN 명령어는 명령어를 실행시키는 구문입니다.
# 기본적으로는 apt를 사용하는 것으로 했지만, yum 등으로 바꿔도 됩니다. (ubuntu에선 주로 apt를 사용합니다)
# RUN은 빌드 시 적용 -> 도커 이미지에 반영됨!
RUN apt update && apt install -y openjdk-17-jdk

# 컨테이너 내의 작업 디렉토리 설정
WORKDIR /spot

# 빌드 결과물 복사
COPY build/libs/*.jar spot-server.jar

# 8080 포트 사용
EXPOSE 8080

# 컨테이너 실행과 함께 spot-server.jar (서버 jar 파일)을 실행
ENTRYPOINT ["java","-jar","spot-server.jar"]