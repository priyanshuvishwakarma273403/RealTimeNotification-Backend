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

    @Transactional
    public ChatRoomResponse createRoomByPhone(com.example.realTimeApp.room.dto.CreateRoomByPhoneRequest request){
        String email = SecurityUtils.getCurrentUsername();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        User otherUser = userRepository.findByPhoneNumber(request.getPhoneNumber())
                .orElseThrow(() -> new RuntimeException("User with this phone number not found"));

        if(currentUser.getId().equals(otherUser.getId())) {
             throw new RuntimeException("Cannot create a chat with yourself");
        }

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
                .userId(otherUser.getId())
                .joinedAt(LocalDateTime.now())
                .build());

        return ChatRoomResponse.builder()
                .roomId(room.getId())
                .type(room.getType())
                .build();
    }

    public java.util.List<com.example.realTimeApp.room.dto.RoomDTO> getUserRooms() {
        String email = SecurityUtils.getCurrentUsername();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        java.util.List<ChatRoomMember> memberships = memberRepository.findByUserId(currentUser.getId());
        java.util.List<com.example.realTimeApp.room.dto.RoomDTO> dtos = new java.util.ArrayList<>();

        for(ChatRoomMember mem : memberships) {
            ChatRoom room = mem.getRoom();
            
            // Find other member
            java.util.List<ChatRoomMember> roomMembers = memberRepository.findByRoom_Id(room.getId());
            User otherUser = null;
            for(ChatRoomMember rm : roomMembers) {
                if(!rm.getUserId().equals(currentUser.getId())) {
                    otherUser = userRepository.findById(rm.getUserId()).orElse(null);
                    break;
                }
            }

            String roomName = otherUser != null ? otherUser.getFullName() : "Unknown Group";
            
            dtos.add(com.example.realTimeApp.room.dto.RoomDTO.builder()
                    .id(room.getId())
                    .receiverId(otherUser != null ? otherUser.getId() : null)
                    .name(roomName)
                    .isOnline(false) // You'd inject PresenceService to check this
                    .unreadCount(0)  // Inject UnreadCacheService
                    .lastMessage("") // Inject ChatMessageRepository
                    .lastMessageTime("")
                    .build());
        }
        return dtos;
    }
}
