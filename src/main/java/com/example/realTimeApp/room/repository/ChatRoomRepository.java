package com.example.realTimeApp.room.repository;

import com.example.realTimeApp.room.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

}
