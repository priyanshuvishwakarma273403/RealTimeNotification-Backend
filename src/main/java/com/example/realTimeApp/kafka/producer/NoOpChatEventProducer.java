package com.example.realTimeApp.kafka.producer;

import com.example.realTimeApp.kafka.event.MessageCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@ConditionalOnProperty(name = "spring.kafka.enabled", havingValue = "false", matchIfMissing = true)
public class NoOpChatEventProducer implements ChatEventPublisher {

    @Override
    public void publishMessageCreated(MessageCreatedEvent event) {
        log.debug("Kafka disabled – skipping event publish for message: {}", event.getMessageId());
    }
}
