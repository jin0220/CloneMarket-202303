package com.example.toy.controller;

import com.example.toy.entity.ChattingContent;
import com.example.toy.entity.ChattingRoom;
import com.example.toy.entity.response.Message;
import com.example.toy.entity.response.StatusEnum;
import com.example.toy.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class ChatController {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    HttpHeaders responseHeaders;

    private final ChatService chatService;

    @PostConstruct
    public void init() {
        responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json; charset=UTF-8");
    }

    @PostMapping("/chattingRoom")
    public ResponseEntity<Message> getChattingRoom(@RequestBody HashMap<String, Object> param){
        Long postNum = Long.parseLong(param.get("postNum").toString());
        String userPhone = param.get("phoneNum").toString();

        ChattingRoom roomChk = chatService.getChattingRoom(postNum, userPhone);

        List<ChattingContent> chat = new ArrayList<>();

        try { // 이미 방이 생성되어 있는 상태라면 이전 대화내용 불러오기
                chat = chatService.getChattingContent(roomChk.getRoomId());
        }
        catch (NullPointerException e){
            log.info(e.getStackTrace().toString());
        }

        Map<String, List<ChattingContent>> map = new HashMap<>();
        map.put("dataList", chat);

        Message message = new Message();
        message.setMessage("fail");
        message.setStatus(StatusEnum.OK);
        message.setData(map);


        return new ResponseEntity<>(message, responseHeaders, HttpStatus.OK);
    }
}
