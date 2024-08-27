package org.depromeet.spot.infrastructure.redis;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import redis.embedded.RedisServer;

@Slf4j
@Configuration
@Profile("local | test")
@RequiredArgsConstructor
public class EmbeddedRedisConfig {

    private final RedisProperties redisProperties;

    private RedisServer redisServer;

    @PostConstruct
    public void redisServer() {
        int port =
                isPortInUse(redisProperties.port()) ? findAvailablePort() : redisProperties.port();
        log.info("embedded redis port = {}", port);
        redisServer = new RedisServer(port);
        redisServer.start();
    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null) {
            redisServer.stop();
        }
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
