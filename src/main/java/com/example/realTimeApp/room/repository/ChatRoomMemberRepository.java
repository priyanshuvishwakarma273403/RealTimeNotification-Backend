package com.example.realTimeApp.room.repository;

import com.example.realTimeApp.room.entity.ChatRoomMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long> {

    List<ChatRoomMember> findByRoom_Id(Long roomId);

    List<ChatRoomMember> findByUserId(Long userId);

    boolean existsByRoom_IdAndUserId(Long roomId, Long userId);
}