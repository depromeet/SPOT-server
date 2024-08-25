package org.depromeet.spot.infrastructure.redis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
    public void redisServer() throws IOException {
        int port = isRedisPortUsed() ? findAvailablePort() : redisProperties.port();
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

    private boolean isRedisPortUsed() throws IOException {
        Process process = getProcessListeningOnPort(redisProperties.port());
        return isRunningProcess(process);
    }

    private Process getProcessListeningOnPort(final int port) throws IOException {
        String command = "lsof -i :" + port + " | grep LISTEN";
        String[] shell = {"/bin/sh", "-c", command};
        return Runtime.getRuntime().exec(shell);
    }

    private boolean isRunningProcess(Process process) {
        try (BufferedReader input =
                new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line = input.readLine();
            if (line == null) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private int findAvailablePort() throws IOException {
        for (int port = 10000; port <= 65535; port++) {
            Process process = getProcessListeningOnPort(port);
            if (!isRunningProcess(process)) {
                return port;
            }
        }
        throw new IllegalArgumentException("10000 ~ 65535 사이에서 사용 가능한 포트가 없습니다.");
    }
}
