package com.example.toy.controller;

import com.example.toy.entity.Member;
import com.example.toy.entity.response.Message;
import com.example.toy.entity.response.StatusEnum;
import com.example.toy.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberController {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    HttpHeaders responseHeaders;

    private final MemberService memberService;

    @PostConstruct
    public void init() {
        responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json; charset=UTF-8");
    }

    @PostMapping("/signup")
    @ResponseBody
    public ResponseEntity<Message> signup(@RequestBody HashMap<String, Object> param) {
        Member member = new Member();
        member.setPhone(param.get("phoneNum").toString());


        Message message = new Message();


        return new ResponseEntity<>(message, responseHeaders, HttpStatus.OK);
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<Message> login(@RequestBody HashMap<String, Object> param) {
        boolean check = memberService.login(param.get("phoneNum").toString());

        Map<String, Boolean> map = new HashMap<>();
        Message message = new Message();

        if(check) {
            log.info("success");
            map.put("result", true);

            message.setMessage("success");
            message.setStatus(StatusEnum.OK);
            message.setData(map);
        }
        else {
            log.info("fail");
            map.put("result", false);

            message.setMessage("fail");
            message.setStatus(StatusEnum.OK);
            message.setData(map);
        }

        return new ResponseEntity<>(message, responseHeaders, HttpStatus.OK);
    }

}
