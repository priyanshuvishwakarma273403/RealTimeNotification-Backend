package com.example.realTimeApp.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Slf4j
public class TypingCacheService {

    private final StringRedisTemplate redisTemplate;

    public void setTyping(Long roomId, Long userId){
        try {
            redisTemplate.opsForValue().set("typing:room:" + roomId + ":user:" + userId, "true", Duration.ofSeconds(5));
        } catch (Exception e) {
            log.warn("Redis unavailable – skipping setTyping for roomId={}, userId={}", roomId, userId);
        }
    }

    public void clearTyping(Long roomId, Long userId) {
        try {
            redisTemplate.delete("typing:room:" + roomId + ":user:" + userId);
        } catch (Exception e) {
            log.warn("Redis unavailable – skipping clearTyping for roomId={}, userId={}", roomId, userId);
        }
    }

}
