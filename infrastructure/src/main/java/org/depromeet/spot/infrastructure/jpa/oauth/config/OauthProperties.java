package org.depromeet.spot.infrastructure.jpa.oauth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.DependsOn;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@ConfigurationProperties(prefix = "oauth")
@DependsOn("awsSecretsManagerConfig")
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
