package com.example.realTimeApp.chat.repository;

import com.example.realTimeApp.chat.document.ChatMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    List<ChatMessage> findByRoomIdOrderByCreatedAtDesc(Long roomId, Pageable pageable);
    List<ChatMessage> findByRoomIdOrderByCreatedAtAsc(Long roomId, Pageable pageable);

}
