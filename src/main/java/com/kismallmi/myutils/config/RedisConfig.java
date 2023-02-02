package com.kismallmi.myutils.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @version 1.0
 * @Author wangjian
 * @since 2023/2/2
 */
@Configuration
public class RedisConfig{

    @Bean
    public RedisTemplate<Object,Object> jsonRedisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<Object,Object> objectObjectRedisTemplate = new RedisTemplate<>();
        objectObjectRedisTemplate.setDefaultSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
        objectObjectRedisTemplate.setConnectionFactory(redisConnectionFactory);
        objectObjectRedisTemplate.setKeySerializer(new StringRedisSerializer());
        return objectObjectRedisTemplate;

    }
}
