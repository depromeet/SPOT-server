package org.depromeet.spot.infrastructure.redis;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import lombok.extern.slf4j.Slf4j;
import redis.embedded.RedisServer;

@Slf4j
@Configuration
@Profile("local | test")
public class EmbeddedRedisConfig {

    private static final String REDISSON_HOST_PREFIX = "redis://localhost:";
    private static final int REDIS_DEFAULT_PORT = 6379;

    private RedisServer redisServer;
    private final int embeddedRedisPort;

    public EmbeddedRedisConfig() {
        this.embeddedRedisPort =
                isPortInUse(REDIS_DEFAULT_PORT) ? findAvailablePort() : REDIS_DEFAULT_PORT;
        log.info("embedded redis port = {}", embeddedRedisPort);
    }

    @PostConstruct
    public void redisServer() {
        redisServer = new RedisServer(embeddedRedisPort);
        redisServer.start();
    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null) {
            redisServer.stop();
        }
    }

    @Bean
    public RedissonClient redissonClient() {
        Config redissonConfig = new Config();
        redissonConfig.useSingleServer().setAddress(REDISSON_HOST_PREFIX + embeddedRedisPort);
        return Redisson.create(redissonConfig);
    }

    private boolean isPortInUse(final int port) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress("localhost", port), 200);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private int findAvailablePort() {
        for (int port = 10000; port <= 65535; port++) {
            if (!isPortInUse(port)) {
                return port;
            }
        }
        throw new IllegalArgumentException("10000 ~ 65535 사이에서 사용 가능한 포트가 없습니다.");
    }
}
