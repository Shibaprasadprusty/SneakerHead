package com.sneakerhead.backend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Redis Configuration with graceful fallback.
 *
 * If Redis is unavailable (e.g., local dev without Redis installed),
 * the app falls back to an in-memory ConcurrentMapCacheManager so
 * the application still starts and runs correctly — just without
 * distributed caching.
 *
 * Cache TTLs (when Redis IS available):
 * - products:all → 5 minutes
 * - products:vendor → 2 minutes
 * - products:brand → 10 minutes
 * - vendor:profile → 15 minutes
 */
@Configuration
@EnableCaching
public class RedisConfig {

        private static final Logger log = LoggerFactory.getLogger(RedisConfig.class);

        public static final String CACHE_PRODUCTS_ALL = "products:all";
        public static final String CACHE_PRODUCTS_VENDOR = "products:vendor";
        public static final String CACHE_PRODUCTS_BRAND = "products:brand";
        public static final String CACHE_VENDOR_PROFILE = "vendor:profile";

        /**
         * RedisTemplate with JSON serialization.
         * Uses RedisSerializer.json() — the Spring Boot 4 standard approach.
         */
        @Bean
        public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
                @SuppressWarnings("unchecked")
                RedisSerializer<Object> jsonSerializer = (RedisSerializer<Object>) RedisSerializer.json();

                RedisTemplate<String, Object> template = new RedisTemplate<>();
                template.setConnectionFactory(factory);
                template.setKeySerializer(new StringRedisSerializer());
                template.setHashKeySerializer(new StringRedisSerializer());
                template.setValueSerializer(jsonSerializer);
                template.setHashValueSerializer(jsonSerializer);
                template.afterPropertiesSet();
                return template;
        }

        /**
         * Primary cache manager — tries Redis first, falls back to in-memory.
         */
        @Bean
        @Primary
        public CacheManager cacheManager(RedisConnectionFactory factory) {
                try {
                        // Test the connection before committing to Redis cache manager
                        factory.getConnection().ping();
                        log.info("✅ Redis connected — using RedisCacheManager");

                        @SuppressWarnings("unchecked")
                        RedisSerializer<Object> jsonSerializer = (RedisSerializer<Object>) RedisSerializer.json();

                        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
                                        .entryTtl(Duration.ofMinutes(5))
                                        .serializeKeysWith(
                                                        RedisSerializationContext.SerializationPair
                                                                        .fromSerializer(new StringRedisSerializer()))
                                        .serializeValuesWith(
                                                        RedisSerializationContext.SerializationPair
                                                                        .fromSerializer(jsonSerializer))
                                        .disableCachingNullValues();

                        Map<String, RedisCacheConfiguration> cacheConfigs = new HashMap<>();
                        cacheConfigs.put(CACHE_PRODUCTS_ALL, defaultConfig.entryTtl(Duration.ofMinutes(5)));
                        cacheConfigs.put(CACHE_PRODUCTS_VENDOR, defaultConfig.entryTtl(Duration.ofMinutes(2)));
                        cacheConfigs.put(CACHE_PRODUCTS_BRAND, defaultConfig.entryTtl(Duration.ofMinutes(10)));
                        cacheConfigs.put(CACHE_VENDOR_PROFILE, defaultConfig.entryTtl(Duration.ofMinutes(15)));

                        return RedisCacheManager.builder(factory)
                                        .cacheDefaults(defaultConfig)
                                        .withInitialCacheConfigurations(cacheConfigs)
                                        .build();

                } catch (Exception e) {
                        log.warn("⚠️  Redis unavailable ({}). Falling back to in-memory cache. " +
                                        "Start Redis to enable distributed caching.", e.getMessage());
                        return new ConcurrentMapCacheManager(
                                        CACHE_PRODUCTS_ALL,
                                        CACHE_PRODUCTS_VENDOR,
                                        CACHE_PRODUCTS_BRAND,
                                        CACHE_VENDOR_PROFILE);
                }
        }
}
