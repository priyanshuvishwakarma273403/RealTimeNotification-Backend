package com.example.realTimeApp.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TypingCacheService {

    private final StringRedisTemplate redisTemplate;

    public void setTyping(Long roomId, Long userId){
        redisTemplate.opsForValue().set("typing:room:" + roomId + ":user:" + userId, "true", Duration.ofSeconds(5));
    }

    public void clearTyping(Long roomId, Long userId) {
        redisTemplate.delete("typing:room:" + roomId + ":user:" + userId);
    }


}
