package org.depromeet.spot.jpa.oauth;

import io.netty.handler.codec.http.HttpHeaderValues;
import lombok.extern.slf4j.Slf4j;
import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.jpa.oauth.entity.KakaoTokenEntity;
import org.depromeet.spot.jpa.oauth.entity.KakaoUserInfoEntity;
import org.depromeet.spot.usecase.port.out.oauth.OauthRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Repository
public class OauthRepositoryImpl implements OauthRepository {

    // kakao에서 발급 받은 clientID
    @Value("${CLIENT_ID}")
    private String CLIENT_ID;

    @Value("${KAUTH_TOKEN_URL_HOST}")
    private String KAUTH_TOKEN_URL_HOST;

    // 엑세스 토큰으로 카카오에서 유저 정보 받아오기
    @Value("${KAUTH_USER_URL_HOST}")
    private String KAUTH_USER_URL_HOST;

    @Override
    public String getKakaoAccessToken(String idCode) {

        // Webflux의 WebClient
        KakaoTokenEntity kakaoTokenEntity = WebClient.create(KAUTH_TOKEN_URL_HOST).post()
            .uri(uriBuilder -> uriBuilder
                .scheme("https")
                .path("/oauth/token")
                .queryParam("grant_type", "authorization_code")
                .queryParam("client_id", CLIENT_ID)
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

        return kakaoTokenEntity.getAccessToken();
    }

    @Override
    public Member getRegisterUserInfo(String accessToken, Member member) {
        KakaoUserInfoEntity userInfo = getUserInfo(accessToken);

        // 회원가입 시 받은 정보를 바탕으로 member로 변환해서 리턴.
        return userInfo.toKakaoDomain(member);
    }

    @Override
    public Member getLoginUserInfo(String accesstoken) {
        KakaoUserInfoEntity userInfo = getUserInfo(accesstoken);

        // TODO : idToken이 변경 될 수 있음. 등록된 email도 변경될 수 있기에 추 후 논의가 필요.
        // 기존 유저와 비교를 위해선 idToken만 필요함.
        return userInfo.toLoginDomain();
    }

    public KakaoUserInfoEntity getUserInfo(String accessToken){
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

        return userInfo;
    }
}

