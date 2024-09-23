package com.hopoong.couponcore.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cache.CacheManager;

import java.time.Duration;

@Configuration
public class LocalCacheConfig {


    @Bean
    public CacheManager localCacheManager() {
        CaffeineCacheManager caffeineCacheManager  = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofSeconds(10)) // 10초
                .maximumSize(1000));

        return caffeineCacheManager;
    }

}
