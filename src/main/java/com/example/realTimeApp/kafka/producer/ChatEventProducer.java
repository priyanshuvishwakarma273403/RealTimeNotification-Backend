package com.example.realTimeApp.kafka.producer;

import com.example.realTimeApp.common.AppConstants;
import com.example.realTimeApp.kafka.event.MessageCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(name = "spring.kafka.enabled", havingValue = "true", matchIfMissing = false)
public class ChatEventProducer implements ChatEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void publishMessageCreated(MessageCreatedEvent event){
        try {
            kafkaTemplate.send(AppConstants.MESSAGE_TOPIC, event);
        } catch (Exception e) {
            log.warn("Kafka not available, skipping event publish for message: {}", event.getMessageId(), e);
        }
    }
}
