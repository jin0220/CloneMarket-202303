package com.example.toy.service;

import com.example.toy.entity.ChattingContent;
import com.example.toy.entity.ChattingRoom;
import com.example.toy.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;

    public void setChat(ChattingRoom chattingRoom) {
//        System.out.println("chattingRoom.getPhoneNum() = " + chattingRoom.getPhoneNum());
//        System.out.println("chattingRoom.getContent() = " + chattingRoom.getContent());

        //chatRepository.save(chattingRoom);
    }

    public ChattingRoom getChattingRoom(Long postNum, String userPhone){
        return chatRepository.findChattingRoom(postNum, userPhone);
    }

    public List<ChattingContent> getChattingContent(Long roomId) {
        return chatRepository.findContentByRoomId(roomId);
    }
}
