package com.example.realTimeApp.chat.dto;

import lombok.Data;

@Data
public class TypingPayload {
    private Long roomId;
    private Long userId;
    private boolean typing;
}
