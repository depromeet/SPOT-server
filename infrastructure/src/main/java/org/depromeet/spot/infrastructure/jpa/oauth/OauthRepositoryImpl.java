package org.depromeet.spot.infrastructure.jpa.oauth;

import org.depromeet.spot.common.exception.oauth.OauthException.InternalOauthServerException;
import org.depromeet.spot.common.exception.oauth.OauthException.InvalidAcessTokenException;
import org.depromeet.spot.domain.member.Member;
import org.depromeet.spot.domain.member.enums.SnsProvider;
import org.depromeet.spot.infrastructure.jpa.oauth.config.OauthProperties;
import org.depromeet.spot.infrastructure.jpa.oauth.entity.GoogleTokenEntity;
import org.depromeet.spot.infrastructure.jpa.oauth.entity.GoogleUserInfoEntity;
import org.depromeet.spot.infrastructure.jpa.oauth.entity.KakaoTokenEntity;
import org.depromeet.spot.infrastructure.jpa.oauth.entity.KakaoUserInfoEntity;
import org.depromeet.spot.usecase.port.out.oauth.OauthRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.handler.codec.http.HttpHeaderValues;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Repository
@RequiredArgsConstructor
public class OauthRepositoryImpl implements OauthRepository {

    private final String BEARER = "Bearer";
    private final OauthProperties properties;

    private final String AUTHORIZATION_CODE = "authorization_code";

    private String KAKAO_REDIRECT_URL = properties.kakaoRedirectUrl();

    private String GOOGLE_REDIRECT_URL = properties.googleRedirectUrl();

    // kakao에서 발급 받은 clientID
    private String KAKAO_CLIENT_ID = properties.kakaoClientId();

    private String GOOGLE_CLIENT_ID = properties.googleClientId();

    private String GOOGLE_CLIENT_SECRET = properties.googleClientSecret();

    private String KAKAO_AUTH_TOKEN_URL_HOST = properties.kakaoAuthTokenUrlHost();

    private String GOOGLE_AUTH_TOKEN_URL_HOST = properties.googleAuthTokenUrlHost();

    // 엑세스 토큰으로 카카오에서 유저 정보 받아오기
    private String KAKAO_AUTH_USER_URL_HOST = properties.kakaoAuthUserUrlHost();

    private String GOOGLE_AUTH_USER_URL_HOST = properties.googleUserUrlHost();

    @Override
    public String getKakaoAccessToken(String authorizationCode) {
        // Webflux의 WebClient
        KakaoTokenEntity kakaoTokenEntity =
                WebClient.create(KAKAO_AUTH_TOKEN_URL_HOST)
                        .post()
                        .uri(
                                uriBuilder ->
                                        uriBuilder
                                                .scheme("https")
                                                .path("/oauth/token")
                                                .queryParam("grant_type", AUTHORIZATION_CODE)
                                                .queryParam("client_id", KAKAO_CLIENT_ID)
                                                .queryParam("code", authorizationCode)
                                                .build(true))
                        .header(
                                HttpHeaders.CONTENT_TYPE,
                                HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                        .retrieve()
                        // TODO : Custom Exception
                        .onStatus(
                                HttpStatusCode::is4xxClientError,
                                clientResponse ->
                                        Mono.error(new RuntimeException("Invalid Parameter")))
                        .onStatus(
                                HttpStatusCode::is5xxServerError,
                                clientResponse ->
                                        Mono.error(new RuntimeException("Internal Server Error")))
                        .bodyToMono(KakaoTokenEntity.class)
                        .block();

        if (kakaoTokenEntity == null) {
            // TODO
        }
        return kakaoTokenEntity.getAccessToken();
    }

    @Override
    public String getOauthAccessToken(SnsProvider snsProvider, String authorizationCode) {
        String authTokenUrlHost;

        switch (snsProvider) {
            case KAKAO:
                authTokenUrlHost = KAKAO_AUTH_TOKEN_URL_HOST;
                break;
            default:
                authTokenUrlHost = GOOGLE_AUTH_TOKEN_URL_HOST;
                break;
        }

        String accessToken =
                WebClient.create(authTokenUrlHost)
                        .post()
                        .uri(
                                uriBuilder -> {
                                    switch (snsProvider) {
                                        case KAKAO:
                                            return uriBuilder
                                                    .scheme("https")
                                                    .path("/oauth/token")
                                                    .queryParam("grant_type", AUTHORIZATION_CODE)
                                                    .queryParam("client_id", KAKAO_CLIENT_ID)
                                                    .queryParam("redirect_uri", KAKAO_REDIRECT_URL)
                                                    .queryParam("code", authorizationCode)
                                                    .build(true);
                                        default: // 기본적으로 GOOGLE 처리
                                            return uriBuilder
                                                    .scheme("https")
                                                    .path("/token")
                                                    .queryParam("grant_type", AUTHORIZATION_CODE)
                                                    .queryParam("client_id", GOOGLE_CLIENT_ID)
                                                    .queryParam(
                                                            "client_secret", GOOGLE_CLIENT_SECRET)
                                                    .queryParam("redirect_uri", GOOGLE_REDIRECT_URL)
                                                    .queryParam("code", authorizationCode)
                                                    .build(true);
                                    }
                                })
                        .header(
                                HttpHeaders.CONTENT_TYPE,
                                HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                        .retrieve()
                        .onStatus(
                                HttpStatusCode::is4xxClientError,
                                clientResponse -> Mono.error(new InvalidAcessTokenException()))
                        .onStatus(
                                HttpStatusCode::is5xxServerError,
                                clientResponse -> Mono.error(new InternalOauthServerException()))
                        .bodyToMono(GoogleTokenEntity.class)
                        .block()
                        .getAccessToken();

        return accessToken;
    }

    @Override
    public Member getKakaoRegisterUserInfo(String accessToken, Member member) {
        KakaoUserInfoEntity userInfo = getKakaoUserInfo(accessToken);

        // 회원가입 시 받은 정보를 바탕으로 member로 변환해서 리턴.
        return userInfo.toKakaoDomain(member);
    }

    @Override
    public Member getOauthRegisterUserInfo(String accessToken, Member member) {
        switch (member.getSnsProvider()) {
            case KAKAO:
                return getKakaoUserInfo(accessToken).toKakaoDomain(member);
            default:
                return getGoogleUserInfo(accessToken).toGoogleDomain(member);
        }
    }

    @Override
    public Member getLoginUserInfo(String accesstoken) {
        KakaoUserInfoEntity userInfo = getKakaoUserInfo(accesstoken);

        // 기존 유저와 비교를 위해선 idToken만 필요함.
        // 앱에서는 accessToken을 반환해주기에 accessToken으로 로직 처리
        return userInfo.toLoginDomain();
    }

    @Override
    public Member getOauthLoginUserInfo(SnsProvider snsProvider, String accesstoken) {
        switch (snsProvider) {
            case KAKAO:
                return getKakaoUserInfo(accesstoken).toLoginDomain();
            default:
                return getGoogleUserInfo(accesstoken).toLoginDomain();
        }
    }

    public KakaoUserInfoEntity getKakaoUserInfo(String accessToken) {
        KakaoUserInfoEntity userInfo =
                WebClient.create(properties.kAuthUserUrlHost())
                        .get()
                        .uri(
                                uriBuilder ->
                                        uriBuilder.scheme("https").path("/v2/user/me").build(true))
                        .header(
                                HttpHeaders.AUTHORIZATION,
                                BEARER + " " + accessToken) // access token 인가
                        .header(
                                HttpHeaders.CONTENT_TYPE,
                                HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                        .retrieve()
                        .onStatus(
                                HttpStatusCode::is4xxClientError,
                                clientResponse -> Mono.error(new InvalidAcessTokenException()))
                        .onStatus(
                                HttpStatusCode::is5xxServerError,
                                clientResponse -> Mono.error(new InternalOauthServerException()))
                        .bodyToMono(KakaoUserInfoEntity.class)
                        .block();

        return userInfo;
    }

    public GoogleUserInfoEntity getGoogleUserInfo(String accessToken) {
        GoogleUserInfoEntity userInfo =
                WebClient.create(GOOGLE_AUTH_USER_URL_HOST)
                        .get()
                        .uri(
                                uriBuilder ->
                                        uriBuilder
                                                .scheme("https")
                                                .path("/oauth2/v3/userinfo")
                                                .build(true))
                        .header(
                                HttpHeaders.AUTHORIZATION,
                                BEARER + " " + accessToken) // access token 인가
                        .header(
                                HttpHeaders.CONTENT_TYPE,
                                HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                        .retrieve()
                        .onStatus(
                                HttpStatusCode::is4xxClientError,
                                clientResponse -> Mono.error(new InvalidAcessTokenException()))
                        .onStatus(
                                HttpStatusCode::is5xxServerError,
                                clientResponse -> Mono.error(new InternalOauthServerException()))
                        .bodyToMono(GoogleUserInfoEntity.class)
                        .block();

        return userInfo;
    }
}
