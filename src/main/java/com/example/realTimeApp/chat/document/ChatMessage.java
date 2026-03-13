package com.example.realTimeApp.chat.document;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "message")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {

    @Id
    private String id;

    private Long roomId;
    private Long senderId;
    private Long receiverId;

    private String content;
    private String type;
    private String status;  // SENT, DELIVERED, READ
    private LocalDateTime createdAt;
    private LocalDateTime deliveredAt;
    private LocalDateTime readAt;

}
