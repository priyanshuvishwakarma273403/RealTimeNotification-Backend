package com.example.realTimeApp.chat;

import com.example.realTimeApp.chat.document.ChatMessage;
import com.example.realTimeApp.chat.dto.MessageResponse;
import com.example.realTimeApp.chat.dto.SendMessageRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/send")
    public MessageResponse sendMessage(@Valid @RequestBody SendMessageRequest request){
        return chatService.sendMessage(request);
    }

    @GetMapping("/rooms/{roomId}/messages")
    public List<ChatMessage> getMessages(@PathVariable Long roomId,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "20") int size) {
        return chatService.getMessages(roomId, page, size);
    }

}
