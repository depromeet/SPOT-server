package org.depromeet.spot.application.common.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;

@Getter
@ConfigurationProperties(prefix = "spring.jwt")
public class JwtProperties {
    String secret;
}
