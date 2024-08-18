package org.depromeet.spot.infrastructure.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "aws.redis")
public record RedisProperties(String host, int port) {}
