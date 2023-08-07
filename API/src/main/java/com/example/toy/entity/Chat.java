package com.example.toy.entity;

import com.example.toy.service.ChatService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "chat")
@Getter @Setter
public class Chat {
    String phoneNum;
    String nickName;
    String content;

    public Chat(String phoneNum, String nickName, String content) {
        this.phoneNum = phoneNum;
        this.nickName = nickName;
        this.content = content;
    }
}
