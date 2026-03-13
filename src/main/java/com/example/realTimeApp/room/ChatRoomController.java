package com.example.realTimeApp.room;

import com.example.realTimeApp.room.dto.ChatRoomResponse;
import com.example.realTimeApp.room.dto.CreatePrivateRoomRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @PostMapping("/private")
    public ChatRoomResponse createPrivateRoom(@RequestBody CreatePrivateRoomRequest request){
        return chatRoomService.createPrivateRoom(request);

    }
}
