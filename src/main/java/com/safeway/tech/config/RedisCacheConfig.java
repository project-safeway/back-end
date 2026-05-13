package com.safeway.tech.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.Map;

@Configuration
public class RedisCacheConfig {

    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory, ObjectMapper objectMapper) {

        ObjectMapper redisObjectMapper = objectMapper.copy();
        redisObjectMapper.activateDefaultTyping(
                redisObjectMapper.getPolymorphicTypeValidator(),
                ObjectMapper.DefaultTyping.NON_FINAL
        );

        GenericJackson2JsonRedisSerializer json = new GenericJackson2JsonRedisSerializer(redisObjectMapper);

        RedisCacheConfiguration defaults = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(json))
                .disableCachingNullValues()
                .prefixCacheNameWith("app::")
                .entryTtl(Duration.ofMinutes(5));

        Map<String, RedisCacheConfiguration> perCache = Map.of(
                "eventos", defaults.entryTtl(Duration.ofMinutes(10))
        );

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(defaults)
                .withInitialCacheConfigurations(perCache)
                .build();
    }

}
