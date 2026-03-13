package com.example.realTimeApp.notification.repository;

import com.example.realTimeApp.notification.document.NotificationDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NotificationRepository extends MongoRepository<NotificationDocument, String> {
    List<NotificationDocument> findByUserIdOrderByCreatedAtDesc(Long userId);
}
