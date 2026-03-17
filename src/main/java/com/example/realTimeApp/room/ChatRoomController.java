package com.example.realTimeApp.room;

import com.example.realTimeApp.room.dto.ChatRoomResponse;
import com.example.realTimeApp.room.dto.CreatePrivateRoomRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.realTimeApp.room.dto.RoomDTO;
import com.example.realTimeApp.room.dto.CreateRoomByPhoneRequest;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @PostMapping("/private")
    public ChatRoomResponse createPrivateRoom(@RequestBody CreatePrivateRoomRequest request){
        return chatRoomService.createPrivateRoom(request);
    }

    @PostMapping("/by-phone")
    public ChatRoomResponse createRoomByPhone(@RequestBody CreateRoomByPhoneRequest request){
        return chatRoomService.createRoomByPhone(request);
    }

    @GetMapping
    public List<RoomDTO> getUserRooms() {
        return chatRoomService.getUserRooms();
    }
}
