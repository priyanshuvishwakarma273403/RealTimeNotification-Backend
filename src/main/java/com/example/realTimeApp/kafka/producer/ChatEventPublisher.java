package com.example.realTimeApp.kafka.producer;

import com.example.realTimeApp.kafka.event.MessageCreatedEvent;

public interface ChatEventPublisher {
    void publishMessageCreated(MessageCreatedEvent event);
}
