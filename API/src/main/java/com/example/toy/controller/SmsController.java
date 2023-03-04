package com.example.toy.controller;

import com.example.toy.entity.response.Message;
import com.example.toy.entity.response.SmsResponse;
import com.example.toy.service.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SmsController {

    private final SmsService smsService;

    HttpHeaders responseHeaders;

    @PostConstruct
    public void init() {
        responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json; charset=UTF-8");
    }


    @PostMapping("/sms")
    @ResponseBody
    public ResponseEntity<Message> sms(@RequestBody HashMap<String, Object> param) throws UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException, InvalidKeyException {
        SmsResponse response = smsService.sendSms(param.get("phoneNum").toString(), param.get("content").toString());

        return null;
    }


}
