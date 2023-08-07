package com.example.toy.service;

import com.example.toy.entity.Chat;
import com.example.toy.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;

    public void setChat(Chat chat) {
        System.out.println("chat.getPhoneNum() = " + chat.getPhoneNum());
        System.out.println("chat.getContent() = " + chat.getContent());

        chatRepository.save(chat);
    }
}
