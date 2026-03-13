package com.example.realTimeApp.room.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatRoomResponse {
    private Long roomId;
    private String type;
}
