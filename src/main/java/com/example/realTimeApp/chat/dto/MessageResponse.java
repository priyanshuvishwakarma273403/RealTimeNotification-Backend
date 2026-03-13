package com.example.realTimeApp.chat.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MessageResponse {

    private String id;
    private Long roomId;
    private Long senderId;
    private Long receiverId;
    private String content;
    private String status;
    private LocalDateTime createdAt;
}
