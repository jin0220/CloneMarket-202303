package com.example.toy.controller;

import com.example.toy.entity.Member;
import com.example.toy.entity.response.Message;
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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import java.util.HashMap;


@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberController {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    HttpHeaders responseHeaders;

    @PostConstruct
    public void init() {
        responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json; charset=UTF-8");
    }

    @PostMapping("/signup")
    @ResponseBody
    public ResponseEntity<Message> signup(@RequestBody HashMap<String, Object> param) {
        Member member = new Member();
        member.setPhoneNum(param.get("phoneNum").toString());
        member.setAuthNo(param.get("authNo").toString());

        Message message = new Message();


        return new ResponseEntity<>(message, responseHeaders, HttpStatus.OK);
    }

}
