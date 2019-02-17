package com.decider.utils.messaginglayer;

import com.decider.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class NotificationPublisher implements MessagePublisher{
    @Autowired
    private RedisTemplate<UUID, Notification> redisTemplate;
    @Autowired
    private ChannelTopic channelTopic;



    public NotificationPublisher() {
    }

    @Override
    public void publish(Notification notification) {
        redisTemplate.convertAndSend(channelTopic.getTopic(),notification);
    }

    @Override
    public void closeConnection() {
        redisTemplate.getConnectionFactory().getConnection().close();
    }
}
