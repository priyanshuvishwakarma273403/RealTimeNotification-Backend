package com.example.realTimeApp.notification.document;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDocument {
    @Id
    private String id;

    private Long userId;
    private String type;
    private String title;
    private String body;
    private String referenceId;
    private Boolean read;
    private LocalDateTime createdAt;
    private LocalDateTime readAt;
}
