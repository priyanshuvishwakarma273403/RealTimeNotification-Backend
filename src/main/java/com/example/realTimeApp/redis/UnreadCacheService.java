package com.example.realTimeApp.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnreadCacheService {
    private final StringRedisTemplate  redisTemplate;

    public void incrementChatUnread(Long userId, Long roomId){
        redisTemplate.opsForValue().increment("unread:user:" + userId + ":room:" + roomId);
    }

    public String getChatUnread(Long userId, Long roomId){
        String value = redisTemplate.opsForValue().get("unread:user:" + userId + ":room:" + roomId);
        return value == null ? "0" : value;
    }

    public void clearChatUnread(Long userId, Long roomId){
        redisTemplate.delete("unread:user:" + userId + ":room:" + roomId);
    }

    public void incrementNotificationUnread(Long userId){
        redisTemplate.opsForValue().increment("notif_unread:user:" + userId);
    }

    public String getNotificationUnread(Long userId){
        String value = redisTemplate.opsForValue().get("notif_unread:user:" + userId);
        return value == null ? "0" : value;
    }

    public void decrementNotificationUnread(Long userId){
        redisTemplate.opsForValue().decrement("notif_unread:user:" + userId);
    }
}
