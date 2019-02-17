package com.decider.configuration;

import com.decider.model.Notification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.util.UUID;

@Configuration
public class MessagingConfiguration {
    @Value("${spring.redis.host}")
    private String host;
    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(host);
        return jedisConnectionFactory;
    }
    @Bean
    ChannelTopic topic() {
        return new ChannelTopic("applicable-rules");
    }

    @Bean
    public RedisTemplate<UUID, Notification> redisTemplate() {
        final RedisTemplate<UUID, Notification> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Notification.class));
        return template;
    }
}