package org.depromeet.spot.infrastructure.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "aws.redis")
public class RedisProperties {
    private String host;
    private int port;
}
