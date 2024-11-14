// package org.depromeet.spot.infrastructure.redis;
//
// import org.redisson.Redisson;
// import org.redisson.api.RedissonClient;
// import org.redisson.config.Config;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.context.annotation.Profile;
//
// import lombok.RequiredArgsConstructor;
//
// @Configuration
// @Profile("dev | prod")
// @RequiredArgsConstructor
// public class RedissonConfig {
//
//    private static final String REDISSON_HOST_PREFIX = "redis://";
//    private final RedisProperties redisProperties;
//
//    @Bean
//    public RedissonClient redissonClient() {
//        Config redissonConfig = new Config();
//        redissonConfig
//                .useSingleServer()
//                .setAddress(
//                        REDISSON_HOST_PREFIX
//                                + redisProperties.host()
//                                + ":"
//                                + redisProperties.port());
//        return Redisson.create(redissonConfig);
//    }
// }
