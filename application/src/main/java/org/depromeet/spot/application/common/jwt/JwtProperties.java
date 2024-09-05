package org.depromeet.spot.application.common.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "spring.jwt")
@DependsOn("awsSecretsManagerConfig")
public class JwtProperties {
    String secret;
}
