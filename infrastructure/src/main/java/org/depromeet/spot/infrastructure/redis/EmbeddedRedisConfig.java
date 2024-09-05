package org.depromeet.spot.infrastructure.redis;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

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

    @Autowired private ResourceLoader resourceLoader;

    public EmbeddedRedisConfig() {
        this.embeddedRedisPort =
                isPortInUse(REDIS_DEFAULT_PORT) ? findAvailablePort() : REDIS_DEFAULT_PORT;
        log.info("embedded redis port = {}", embeddedRedisPort);
    }

    @PostConstruct
    public void redisServer() {
        try {
            if (isArmMac()) {
                File redisFile = getRedisFileForArcMac();
                if (redisFile == null || !redisFile.exists()) {
                    throw new RuntimeException("Redis binary file not found for ARM Mac");
                }
                redisServer = new RedisServer(redisFile, embeddedRedisPort);
            } else {
                redisServer =
                        RedisServer.builder()
                                .port(embeddedRedisPort)
                                .setting("maxmemory 128M")
                                .build();
            }
            redisServer.start();
            log.info("Embedded Redis server started on port {}", embeddedRedisPort);
        } catch (Exception e) {
            log.error("Failed to start embedded Redis server", e);
            throw new RuntimeException("Failed to start embedded Redis server", e);
        }
    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null && redisServer.isActive()) {
            redisServer.stop();
            log.info("Embedded Redis server stopped");
        }
    }

    @Bean
    public RedissonClient redissonClient() {
        Config redissonConfig = new Config();
        redissonConfig.useSingleServer().setAddress(REDISSON_HOST_PREFIX + embeddedRedisPort);
        return Redisson.create(redissonConfig);
    }

    /**
     * 현재 시스템이 ARM 아키텍처를 사용하는 MAC인지 확인 System.getProperty("os.arch") : JVM이 실행되는 시스템 아키텍처 반환
     * System.getProperty("os.name") : 시스템 이름 반환
     */
    private boolean isArmMac() {
        String osArch = System.getProperty("os.arch");
        String osName = System.getProperty("os.name");
        return (osArch != null && osArch.equals("aarch64"))
                && (osName != null && osName.equals("Mac OS X"));
    }

    private File getRedisFileForArcMac() {
        try {
            Resource resource =
                    resourceLoader.getResource(
                            "classpath:binary/redis/redis-server-7.2.3-mac-arm64");
            if (!resource.exists()) {
                throw new IOException("Redis binary file not found");
            }

            File tempFile = File.createTempFile("redis-server", ".tmp");
            tempFile.deleteOnExit();

            try (InputStream is = resource.getInputStream()) {
                Files.copy(is, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }

            tempFile.setExecutable(true);
            return tempFile;
        } catch (IOException e) {
            log.error("Failed to load Redis binary file", e);
            throw new RuntimeException("Failed to load Redis binary file", e);
        }
    }

    private boolean isPortInUse(final int port) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress("localhost", port), 500);
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
