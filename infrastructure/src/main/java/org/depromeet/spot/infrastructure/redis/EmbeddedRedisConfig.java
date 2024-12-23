// package org.depromeet.spot.infrastructure.redis;
//
// import java.io.File;
// import java.io.IOException;
// import java.net.InetSocketAddress;
// import java.net.Socket;
// import java.util.Objects;
//
// import jakarta.annotation.PostConstruct;
// import jakarta.annotation.PreDestroy;
//
// import org.redisson.Redisson;
// import org.redisson.api.RedissonClient;
// import org.redisson.config.Config;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.context.annotation.Profile;
// import org.springframework.core.io.ClassPathResource;
//
// import lombok.extern.slf4j.Slf4j;
// import redis.embedded.RedisServer;
//
// @Slf4j
// @Configuration
// @Profile("local | test")
// public class EmbeddedRedisConfig {
//
//    private static final String REDISSON_HOST_PREFIX = "redis://localhost:";
//    private static final int REDIS_DEFAULT_PORT = 6379;
//
//    private RedisServer redisServer;
//    private final int embeddedRedisPort;
//
//    public EmbeddedRedisConfig() {
//        this.embeddedRedisPort =
//                isPortInUse(REDIS_DEFAULT_PORT) ? findAvailablePort() : REDIS_DEFAULT_PORT;
//        log.info("embedded redis port = {}", embeddedRedisPort);
//    }
//
//    @PostConstruct
//    public void redisServer() {
//        //        redisServer = new RedisServer(embeddedRedisPort);
//        if (isArmMac()) {
//            redisServer = new RedisServer(getRedisFileForArcMac(), embeddedRedisPort);
//        } else {
//            redisServer =
//                    RedisServer.builder()
//                            .port(embeddedRedisPort)
//                            .setting("maxmemory 128M") // maxheap 128M
//                            .build();
//        }
//        try {
//            redisServer.start();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @PreDestroy
//    public void stopRedis() {
//        if (redisServer != null) {
//            redisServer.stop();
//        }
//    }
//
//    @Bean
//    public RedissonClient redissonClient() {
//        Config redissonConfig = new Config();
//        redissonConfig.useSingleServer().setAddress(REDISSON_HOST_PREFIX + embeddedRedisPort);
//        return Redisson.create(redissonConfig);
//    }
//
//    /**
//     * 현재 시스템이 ARM 아키텍처를 사용하는 MAC인지 확인 System.getProperty("os.arch") : JVM이 실행되는 시스템 아키텍처 반환
//     * System.getProperty("os.name") : 시스템 이름 반환
//     */
//    private boolean isArmMac() {
//        return Objects.equals(System.getProperty("os.arch"), "aarch64")
//                && Objects.equals(System.getProperty("os.name"), "Mac OS X");
//    }
//
//    /** ARM 아키텍처를 사용하는 Mac에서 실행할 수 있는 Redis 바이너리 파일을 반환 */
//    private File getRedisFileForArcMac() {
//        try {
//            return new ClassPathResource("binary/redis/redis-server-7.2.3-mac-arm64").getFile();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    private boolean isPortInUse(final int port) {
//        try (Socket socket = new Socket()) {
//            socket.connect(new InetSocketAddress("localhost", port), 200);
//            return true;
//        } catch (IOException e) {
//            return false;
//        }
//    }
//
//    private int findAvailablePort() {
//        for (int port = 10000; port <= 65535; port++) {
//            if (!isPortInUse(port)) {
//                return port;
//            }
//        }
//        throw new IllegalArgumentException("10000 ~ 65535 사이에서 사용 가능한 포트가 없습니다.");
//    }
// }
