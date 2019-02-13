package com.example.decider.utils.messaginglayer;

import com.example.decider.model.Notification;

public interface MessagePublisher {
    void publish(Notification notification);
    void closeConnection();
}
