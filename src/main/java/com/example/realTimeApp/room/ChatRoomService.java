package com.example.realTimeApp.room;

import com.example.realTimeApp.common.util.SecurityUtils;
import com.example.realTimeApp.room.dto.ChatRoomResponse;
import com.example.realTimeApp.room.dto.CreatePrivateRoomRequest;
import com.example.realTimeApp.room.entity.ChatRoom;
import com.example.realTimeApp.room.entity.ChatRoomMember;
import com.example.realTimeApp.room.repository.ChatRoomMemberRepository;
import com.example.realTimeApp.room.repository.ChatRoomRepository;
import com.example.realTimeApp.user.entity.User;
import com.example.realTimeApp.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository   chatRoomRepository;
    private final ChatRoomMemberRepository  memberRepository;
    private final UserRepository userRepository;

    @Transactional
    public ChatRoomResponse createPrivateRoom(CreatePrivateRoomRequest request){
        String email = SecurityUtils.getCurrentUsername();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userRepository.findById(request.getOtherUserId())
                .orElseThrow(() -> new RuntimeException("Other user not found"));

        ChatRoom room = ChatRoom.builder()
                .type("PRIVATE")
                .createdAt(LocalDateTime.now())
                .build();
        room = chatRoomRepository.save(room);

        memberRepository.save(ChatRoomMember.builder()
                .room(room)
                .userId(currentUser.getId())
                .joinedAt(LocalDateTime.now())
                .build());

        memberRepository.save(ChatRoomMember.builder()
                .room(room)
                .userId(request.getOtherUserId())
                .joinedAt(LocalDateTime.now())
                .build());

        return ChatRoomResponse.builder()
                .roomId(room.getId())
                .type(room.getType())
                .build();

    }
}
