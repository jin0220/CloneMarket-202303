package com.example.toy.service;

import com.example.toy.dto.ChattingRoomDto;
import com.example.toy.entity.ChattingContent;
import com.example.toy.entity.ChattingRoom;
import com.example.toy.repository.ChattingContentRepository;
import com.example.toy.repository.ChattingRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChattingRoomRepository chattingRoomRepository;
    private final ChattingContentRepository chattingContentRepository;

    public ChattingRoom setChattingRoom(ChattingRoom chattingRoom){
        return chattingRoomRepository.save(chattingRoom);
    }

    public void setChattingContent(ChattingContent chattingContent) {
//        System.out.println("chattingRoom.getPhoneNum() = " + chattingRoom.getPhoneNum());
//        System.out.println("chattingRoom.getContent() = " + chattingRoom.getContent());

        chattingContentRepository.save(chattingContent);
    }

    public ChattingRoom getChattingRoom(Long postNum, String userPhone){
        return chattingRoomRepository.findChattingRoom(postNum, userPhone);
    }

    public List<ChattingRoomDto> getRoomList(String userPhone){
//        return chattingRoomRepository.findByBuyerUser(userPhone);
        return chattingRoomRepository.getRoomList(userPhone);
    }

    public List<ChattingContent> getChattingContent(Long roomId) {
        return chattingContentRepository.findByRoomId(roomId);
    }
}
