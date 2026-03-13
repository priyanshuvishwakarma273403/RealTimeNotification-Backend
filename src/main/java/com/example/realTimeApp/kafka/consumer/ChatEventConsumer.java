package com.example.realTimeApp.kafka.consumer;

import com.example.realTimeApp.kafka.event.MessageCreatedEvent;
import com.example.realTimeApp.notification.NotificationService;
import com.example.realTimeApp.user.entity.User;
import com.example.realTimeApp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatEventConsumer {

    private final NotificationService notificationService;
    private final UserRepository userRepository;

    @KafkaListener(topics = "chat-message-created", groupId = "realtime-app-group")
    public void consumeMessageCreated(MessageCreatedEvent event){
        User sender = userRepository.findById(event.getSenderId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        notificationService.createMessageNotification(
                event.getReceiverId(),
                event.getMessageId(),
                sender.getFullName()
        );
    }
}
