# ubuntu 20.04 버전을 기반으로 합니다.
FROM ubuntu:20.04

# RUN 명령어는 명령어를 실행시키는 구문입니다.
# 기본적으로는 apt를 사용하는 것으로 했지만, yum 등으로 바꿔도 됩니다. (ubuntu에선 주로 apt를 사용합니다)
# RUN은 빌드 시 적용 -> 도커 이미지에 반영됨!
RUN apt update && apt install -y java

# 작업하게 될 디렉토리입니다. 없을 경우 생성해요!
WORKDIR /spot/server

# 서버를 실행하는 명령어 입니다.
RUN java -jar SPOT-server-0.0.1.jar

# CMD는 컨테이너에 반영됨.