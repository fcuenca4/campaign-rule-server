package com.decider.service;

import com.decider.exception.PublishException;
import com.decider.model.Context;
import com.decider.model.Notification;
import com.decider.model.builder.NotificationBuilder;
import com.decider.model.strategy.CampaignRule;
import com.decider.utils.messaginglayer.NotificationPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    @Autowired
    NotificationPublisher notificationPublisher;
    @Autowired
    NotificationBuilder notificationBuilder;
    public void publish(Context context, List<CampaignRule> availableRules) throws PublishException {
        Notification newObject = notificationBuilder.build();
        newObject.setContext(context);
        newObject.setCampaignRules(availableRules);
        try{
            notificationPublisher.publish(newObject);
        }catch(Exception e){
            e.printStackTrace();
            throw new PublishException("Publish Error");
        }finally {
            notificationPublisher.closeConnection();
        }
    }
}
