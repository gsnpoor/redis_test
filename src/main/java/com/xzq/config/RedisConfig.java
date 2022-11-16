package com.xzq.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.net.UnknownHostException;

/**
 * @Author xiaozq
 * @Date 2022/11/7 15:07
 * <p>@Description:</p>
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {

        // jackson序列化
        Jackson2JsonRedisSerializer jackson = new Jackson2JsonRedisSerializer(Object.class);
        // 创建objectMapper，jackson序列化需要配置objectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        // 设置ObjectMapper
        jackson.setObjectMapper(objectMapper);

        // String序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        // 创建返回实体类,设置redistemplate相关信息
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        // key&&hashKey序列化用String序列化
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        // value&&hashValue序列化用jackson序列化
        template.setValueSerializer(jackson);
        template.setHashValueSerializer(jackson);
        template.afterPropertiesSet();

        return template;
    }


}
