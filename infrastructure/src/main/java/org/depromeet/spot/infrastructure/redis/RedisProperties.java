package org.depromeet.spot.infrastructure.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "aws.redis")
public class RedisProperties {
    private String host;
    private int port;
}
