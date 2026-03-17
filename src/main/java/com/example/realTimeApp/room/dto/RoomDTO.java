package com.example.realTimeApp.room.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoomDTO {
    private Long id;
    private Long receiverId;
    private String name;
    private boolean isOnline;
    private int unreadCount;
    private String lastMessage;
    private String lastMessageTime;
}
