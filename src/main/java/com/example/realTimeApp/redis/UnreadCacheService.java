package com.example.realTimeApp.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UnreadCacheService {
    private final StringRedisTemplate  redisTemplate;

    public void incrementChatUnread(Long userId, Long roomId){
        try {
            redisTemplate.opsForValue().increment("unread:user:" + userId + ":room:" + roomId);
        } catch (Exception e) {
            log.warn("Redis unavailable – skipping incrementChatUnread for userId={}", userId);
        }
    }

    public String getChatUnread(Long userId, Long roomId){
        try {
            String value = redisTemplate.opsForValue().get("unread:user:" + userId + ":room:" + roomId);
            return value == null ? "0" : value;
        } catch (Exception e) {
            log.warn("Redis unavailable – returning 0 for chatUnread userId={}", userId);
            return "0";
        }
    }

    public void clearChatUnread(Long userId, Long roomId){
        try {
            redisTemplate.delete("unread:user:" + userId + ":room:" + roomId);
        } catch (Exception e) {
            log.warn("Redis unavailable – skipping clearChatUnread for userId={}", userId);
        }
    }

    public void incrementNotificationUnread(Long userId){
        try {
            redisTemplate.opsForValue().increment("notif_unread:user:" + userId);
        } catch (Exception e) {
            log.warn("Redis unavailable – skipping incrementNotificationUnread for userId={}", userId);
        }
    }

    public String getNotificationUnread(Long userId){
        try {
            String value = redisTemplate.opsForValue().get("notif_unread:user:" + userId);
            return value == null ? "0" : value;
        } catch (Exception e) {
            log.warn("Redis unavailable – returning 0 for notificationUnread userId={}", userId);
            return "0";
        }
    }

    public void decrementNotificationUnread(Long userId){
        try {
            redisTemplate.opsForValue().decrement("notif_unread:user:" + userId);
        } catch (Exception e) {
            log.warn("Redis unavailable – skipping decrementNotificationUnread for userId={}", userId);
        }
    }
}
