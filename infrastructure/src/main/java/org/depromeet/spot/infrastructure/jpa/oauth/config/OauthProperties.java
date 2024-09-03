package org.depromeet.spot.infrastructure.jpa.oauth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@ConfigurationProperties(prefix = "oauth")
public class OauthProperties {
    String kakaoClientId;
    String kakaoAuthTokenUrlHost;
    String kakaoAuthUserUrlHost;
    String kakaoRedirectUrl;
    String googleClientId;
    String googleClientSecret;
    String googleRedirectUrl;
    String googleAuthTokenUrlHost;
    String googleAuthUserUrlHost;
}
