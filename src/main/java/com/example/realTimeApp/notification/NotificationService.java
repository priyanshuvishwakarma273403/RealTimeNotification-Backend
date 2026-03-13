package com.example.realTimeApp.notification;

import com.example.realTimeApp.common.util.SecurityUtils;
import com.example.realTimeApp.notification.document.NotificationDocument;
import com.example.realTimeApp.notification.dto.NotificationResponse;
import com.example.realTimeApp.notification.repository.NotificationRepository;
import com.example.realTimeApp.redis.UnreadCacheService;
import com.example.realTimeApp.user.entity.User;
import com.example.realTimeApp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final UnreadCacheService unreadCacheService;
    private final SimpMessagingTemplate messagingTemplate;

    public void createMessageNotification(Long userId, String referenceId, String senderName){
        NotificationDocument doc = notificationRepository.save(NotificationDocument.builder()
                .userId(userId)
                .type("NEW_MESSAGE")
                .title("New message")
                .body(senderName + " sent you a message")
                .referenceId(referenceId)
                .read(false)
                .createdAt(LocalDateTime.now())
                .build());

        unreadCacheService.incrementNotificationUnread(userId);

        messagingTemplate.convertAndSend("/topic/notifications." + userId,
                NotificationResponse.builder()
                        .id(doc.getId())
                        .type(doc.getType())
                        .title(doc.getTitle())
                        .body(doc.getBody())
                        .read(doc.getRead())
                        .createdAt(doc.getCreatedAt())
                        .build());
    }

    public List<NotificationResponse> getMyNotifications(){
        String email = SecurityUtils.getCurrentUsername();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return notificationRepository.findByUserIdOrderByCreatedAtDesc(user.getId())
                .stream()
                .map(n -> NotificationResponse.builder()
                        .id(n.getId())
                        .type(n.getType())
                        .title(n.getTitle())
                        .body(n.getBody())
                        .read(n.getRead())
                        .createdAt(n.getCreatedAt())
                        .build())
                .toList();
    }

    public void markRead(String notificationId) {
        NotificationDocument doc = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        if (!Boolean.TRUE.equals(doc.getRead())) {
            doc.setRead(true);
            doc.setReadAt(LocalDateTime.now());
            notificationRepository.save(doc);
            unreadCacheService.decrementNotificationUnread(doc.getUserId());
        }
    }
}
