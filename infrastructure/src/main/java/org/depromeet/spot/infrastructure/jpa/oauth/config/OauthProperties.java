package org.depromeet.spot.infrastructure.jpa.oauth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oauth")
public record OauthProperties(
        String kakaoClientId,
        String kakaoAuthTokenUrlHost,
        String kakaoAuthUserUrlHost,
        String kakaoRedirectUrl,
        String googleClientId,
        String googleClientSecret,
        String googleRedirectUrl,
        String googleAuthTokenUrlHost,
        String googleUserUrlHost) {}
