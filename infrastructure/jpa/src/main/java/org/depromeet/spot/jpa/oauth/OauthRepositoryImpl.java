package org.depromeet.spot.jpa.oauth;

import io.netty.handler.codec.http.HttpHeaderValues;
import lombok.extern.slf4j.Slf4j;
import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.jpa.oauth.entity.KakaoTokenEntity;
import org.depromeet.spot.jpa.oauth.entity.KakaoUserInfoEntity;
import org.depromeet.spot.usecase.port.out.oauth.OauthRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Repository
public class OauthRepositoryImpl implements OauthRepository {

    @Override
    public String getKakaoAccessToken(String idCode) {
        String KAUTH_TOKEN_URL_HOST ="https://kauth.kakao.com";

        // kakao에서 발급 받은 clientID
        String clientId = "1f043adfbb8f5438907686f472d3b164";

        // Webflux의 WebClient
        KakaoTokenEntity kakaoTokenEntity = WebClient.create(KAUTH_TOKEN_URL_HOST).post()
            .uri(uriBuilder -> uriBuilder
                .scheme("https")
                .path("/oauth/token")
                .queryParam("grant_type", "authorization_code")
                .queryParam("client_id", clientId)
                .queryParam("code", idCode)
                .build(true))
            .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
            .retrieve()
            // TODO : Custom Exception
            .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException("Invalid Parameter")))
            .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new RuntimeException("Internal Server Error")))
            .bodyToMono(KakaoTokenEntity.class)
            .block();


        log.info("Access Token : {}", kakaoTokenEntity.getAccessToken());
        log.info("Refresh Token : {}", kakaoTokenEntity.getRefreshToken());
//        //제공 조건: OpenID Connect가 활성화 된 앱의 토큰 발급 요청인 경우 또는 scope에 openid를 포함한 추가 항목 동의 받기 요청을 거친 토큰 발급 요청인 경우
//        log.info("Id Token : {}", kakaoTokenResponseDto.getIdToken());
//        log.info("Scope : {}", kakaoTokenResponseDto.getScope());

        return kakaoTokenEntity.getAccessToken();
    }

    @Override
    public Member getUserInfo(String accessToken, Member member) {
        // 엑세스 토큰으로 카카오에서 유저 정보 받아오기
        String KAUTH_USER_URL_HOST = "https://kapi.kakao.com";

        KakaoUserInfoEntity userInfo = WebClient.create(KAUTH_USER_URL_HOST)
            .get()
            .uri(uriBuilder -> uriBuilder
                .scheme("https")
                .path("/v2/user/me")
                .build(true))
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken) // access token 인가
            .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
            .retrieve()
            // TODO : Custom Exception
            .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException("Invalid Parameter")))
            .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new RuntimeException("Internal Server Error")))
            .bodyToMono(KakaoUserInfoEntity.class)
            .block();

        log.info("kakao AuthId : {} ", userInfo.getId());
        log.info("nickname : {} ", userInfo.getKakaoAccount().getProfile().getNickName());
        log.info("ProfileImageUrl : {} ", userInfo.getKakaoAccount().getProfile().getProfileImageUrl());
        log.info("kakao user info : {}", userInfo);

        // member로 변환해서 리턴.
        return userInfo.toKakaoDomain(member);
    }
}

