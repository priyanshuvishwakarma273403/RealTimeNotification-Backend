package com.example.realTimeApp.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class PresenceService {
    private final StringRedisTemplate redisTemplate;

    public void setOnline(Long userId){
        try {
            redisTemplate.opsForValue().set("online:user:" + userId, "true", Duration.ofHours(2));
            redisTemplate.opsForValue().set("lastseen:user:" + userId, LocalDateTime.now().toString());
        } catch (Exception e) {
            log.warn("Redis unavailable – skipping setOnline for userId={}", userId);
        }
    }

    public void setOffline(Long userId){
        try {
            redisTemplate.delete("online:user:" + userId);
            redisTemplate.opsForValue().set("lastseen:user:" + userId, LocalDateTime.now().toString());
        } catch (Exception e) {
            log.warn("Redis unavailable – skipping setOffline for userId={}", userId);
        }
    }

    public boolean isOnline(Long userId){
        try {
            return "true".equals(redisTemplate.opsForValue().get("online:user:" + userId));
        } catch (Exception e) {
            log.warn("Redis unavailable – returning false for isOnline userId={}", userId);
            return false;
        }
    }
}
