package com.example.realTimeApp.room.dto;

import lombok.Data;

@Data
public class CreatePrivateRoomRequest {
    private Long otherUserId;
}
