package com.example.toy.controller;

import com.example.toy.entity.response.Message;
import com.example.toy.entity.response.SmsResponse;
import com.example.toy.entity.response.StatusEnum;
import com.example.toy.service.SmsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SmsController {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final SmsService smsService;

    HttpHeaders responseHeaders;

    @PostConstruct
    public void init() {
        responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json; charset=UTF-8");
    }


    @PostMapping("/sms")
    @ResponseBody
    public ResponseEntity<Message> sms(@RequestBody HashMap<String, Object> param) throws UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException, InvalidKeyException, JsonProcessingException {
        SmsResponse response = smsService.sendSms(param.get("phoneNum").toString());

        Message message = new Message();
        Map<String, String> map  = new HashMap<>();

        if(response.getStatusName().equals("success")) {
            log.info("success");
            map.put("authNum", response.getAuthNum());

            message.setMessage("Success");
            message.setStatus(StatusEnum.OK);
            message.setData(map);
        }
        else {
            log.info("Fail");
            map.put("authNum", null);

            message.setMessage("Fail");
            message.setStatus(StatusEnum.OK);
            message.setData(map);
        }

        return new ResponseEntity<>(message, responseHeaders, HttpStatus.OK);
    }


}
