package com.example.realTimeApp.notification.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class NotificationResponse {
    private String id;
    private String type;
    private String title;
    private String body;
    private Boolean read;
    private LocalDateTime createdAt;
}
