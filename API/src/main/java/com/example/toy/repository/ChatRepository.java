package com.example.toy.repository;

import com.example.toy.entity.ChattingContent;
import com.example.toy.entity.ChattingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<ChattingRoom, String> {
    @Query(value = "select * from chatting_room where post_num=:postNum and buyer_user=:userPhone", nativeQuery = true)
    ChattingRoom findChattingRoom(@Param("postNum") Long postNum, @Param("userPhone") String phoneNum);
    List<ChattingContent> findContentByRoomId(Long roomId);
}
