package com.example.realTimeApp.room.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateRoomByPhoneRequest {
    @NotBlank
    private String phoneNumber;
}
