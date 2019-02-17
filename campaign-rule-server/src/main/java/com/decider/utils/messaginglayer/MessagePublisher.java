package com.decider.utils.messaginglayer;

import com.decider.model.Notification;

public interface MessagePublisher {
    void publish(Notification notification);
    void closeConnection();
}
