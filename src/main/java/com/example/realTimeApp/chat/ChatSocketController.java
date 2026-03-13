package com.example.realTimeApp.chat;

import com.example.realTimeApp.chat.dto.TypingPayload;
import com.example.realTimeApp.redis.TypingCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatSocketController {
    private final SimpMessagingTemplate messagingTemplate;
    private final TypingCacheService typingCacheService;

    @MessageMapping("/chat.typing")
    public void typing(@Payload TypingPayload payload) {
        if(payload.isTyping()){
            typingCacheService.setTyping(payload.getRoomId(), payload.getUserId());
        } else{
            typingCacheService.clearTyping(payload.getRoomId(), payload.getUserId());
        }
        messagingTemplate.convertAndSend("/topic/typing." + payload.getRoomId(), payload);
    }
}
