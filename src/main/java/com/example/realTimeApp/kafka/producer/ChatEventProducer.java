package com.example.realTimeApp.kafka.producer;

import com.example.realTimeApp.common.AppConstants;
import com.example.realTimeApp.kafka.event.MessageCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishMessageCreated(MessageCreatedEvent event){
        kafkaTemplate.send(AppConstants.MESSAGE_TOPIC, event);
    }
}
