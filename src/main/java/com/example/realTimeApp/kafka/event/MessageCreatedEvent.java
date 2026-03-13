package com.example.realTimeApp.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageCreatedEvent {
    private String messageId;
    private Long roomId;
    private Long senderId;
    private Long receiverId;
    private String content;
    private LocalDateTime createdAt;
}

