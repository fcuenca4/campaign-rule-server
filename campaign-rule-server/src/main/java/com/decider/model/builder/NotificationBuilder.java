package com.decider.model.builder;

import com.decider.model.Notification;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationBuilder {
    @Autowired
    ObjectFactory<Notification> notificationObjectFactory;

    public Notification build(){
        return notificationObjectFactory.getObject();
    }
}
