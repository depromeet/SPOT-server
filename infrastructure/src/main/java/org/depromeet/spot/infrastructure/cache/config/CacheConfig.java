package org.depromeet.spot.infrastructure.cache.config;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.depromeet.spot.infrastructure.cache.CacheType;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Scheduler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableCaching
@Configuration
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        getCaches().forEach(cache -> cacheManager.setCaches(getCaches()));
        return cacheManager;
    }

    public List<Cache> getCaches() {
        return Arrays.stream(CacheType.values()).map(this::createCache).toList();
    }

    private Cache createCache(CacheType type) {
        return new CaffeineCache(
                type.getName(),
                Caffeine.newBuilder()
                        .softValues()
                        .recordStats()
                        .maximumSize(type.getSize())
                        .scheduler(Scheduler.systemScheduler())
                        .expireAfterWrite(type.getExpireSeconds(), TimeUnit.SECONDS)
                        .removalListener(
                                ((key, value, cause) ->
                                        log.debug(
                                                "key: {}, value: {}의 캐시가 {}로 인해 제거되었어요.",
                                                key,
                                                value,
                                                cause)))
                        .build());
    }
}
