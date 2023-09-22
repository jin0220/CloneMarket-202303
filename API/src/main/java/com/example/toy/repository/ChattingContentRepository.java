package com.example.toy.repository;

import com.example.toy.entity.ChattingContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChattingContentRepository extends JpaRepository<ChattingContent,Long> {
    List<ChattingContent> findByRoomId(Long roomId);
}
