package com.example.realTimeApp.notification;

import com.example.realTimeApp.notification.dto.NotificationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public List<NotificationResponse> getMyNotifications() {
        return notificationService.getMyNotifications();
    }

    @PutMapping("/{notificationId}/read")
    public void markRead(@PathVariable String notificationId) {
        notificationService.markRead(notificationId);
    }
}
