package com.example.realTimeApp.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PresenceService {
    private final StringRedisTemplate redisTemplate;

    public void setOnline(Long userId){
        redisTemplate.opsForValue().set("online:user:" + userId, "true", Duration.ofHours(2));
        redisTemplate.opsForValue().set("lastseen:user:" + userId, LocalDateTime.now().toString());
    }

    public void setOffline(Long userId){
        redisTemplate.delete("online:user:" + userId);
        redisTemplate.opsForValue().set("lastseen:user:" + userId, LocalDateTime.now().toString());
    }

    public boolean isOnline(Long userId){
        return "true".equals(redisTemplate.opsForValue().get("online:user:" + userId));
    }
}
