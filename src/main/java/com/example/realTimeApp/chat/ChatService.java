package com.example.realTimeApp.chat;

import com.example.realTimeApp.chat.document.ChatMessage;
import com.example.realTimeApp.chat.dto.MessageResponse;
import com.example.realTimeApp.chat.dto.SendMessageRequest;
import com.example.realTimeApp.chat.repository.ChatMessageRepository;
import com.example.realTimeApp.common.util.SecurityUtils;
import com.example.realTimeApp.kafka.event.MessageCreatedEvent;
import com.example.realTimeApp.kafka.producer.ChatEventProducer;
import com.example.realTimeApp.redis.UnreadCacheService;
import com.example.realTimeApp.user.entity.User;
import com.example.realTimeApp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
    private final ChatEventProducer chatEventProducer;
    private final SimpMessagingTemplate messagingTemplate;
    private final UnreadCacheService unreadCacheService;

    public MessageResponse sendMessage(SendMessageRequest request){
        String email = SecurityUtils.getCurrentUsername();
        User sender = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        ChatMessage saved = chatMessageRepository.save(ChatMessage.builder()
                .roomId(request.getRoomId())
                .senderId(sender.getId())
                .receiverId(request.getReceiverId())
                .content(request.getContent())
                .type("TEXT")
                .status("SENT")
                .createdAt(LocalDateTime.now())
                .build());

        MessageResponse response  = MessageResponse.builder()
                .id(saved.getId())
                .roomId(saved.getRoomId())
                .senderId(saved.getSenderId())
                .receiverId(saved.getReceiverId())
                .content(saved.getContent())
                .status(saved.getStatus())
                .createdAt(saved.getCreatedAt())
                .build();

        messagingTemplate.convertAndSend("/topic/room." + request.getRoomId(), response);

        chatEventProducer.publishMessageCreated(MessageCreatedEvent.builder()
                .messageId(saved.getId())
                .roomId(saved.getRoomId())
                .senderId(saved.getSenderId())
                .receiverId(saved.getReceiverId())
                .content(saved.getContent())
                .createdAt(saved.getCreatedAt())
                .build());

        unreadCacheService.incrementChatUnread(request.getReceiverId(), request.getRoomId());
        return response;
    }

    public List<ChatMessage> getMessages(Long roomId, int page, int size) {
        return chatMessageRepository.findByRoomIdOrderByCreatedAtDesc(roomId, PageRequest.of(page, size));
    }

}
