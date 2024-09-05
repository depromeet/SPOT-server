package org.depromeet.spot.infrastructure.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.DependsOn;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "aws.redis")
@DependsOn("awsSecretsManagerConfig")
public class RedisProperties {
    private String host;
    private int port;
}
