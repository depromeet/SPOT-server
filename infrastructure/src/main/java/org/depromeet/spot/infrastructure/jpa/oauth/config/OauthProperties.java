package org.depromeet.spot.infrastructure.jpa.oauth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oauth")
public record OauthProperties(String clientId, String kAuthTokenUrlHost, String kAuthUserUrlHost) {}
