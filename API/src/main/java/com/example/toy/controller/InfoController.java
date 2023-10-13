package com.example.toy.controller;

import com.example.toy.entity.Info;
import com.example.toy.entity.response.Message;
import com.example.toy.entity.response.StatusEnum;
import com.example.toy.service.InfoService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class InfoController {
    private final InfoService infoService;

    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    HttpHeaders responseHeaders;

    @Value("${file.upload.path}")
    private String saveDir;


    @PostConstruct
    public void init() {
        responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json; charset=UTF-8");
    }

    @GetMapping("/infoList")
    @ResponseBody
    public ResponseEntity<Message> getInfoList(@Param("page") int page) {
        log.info("connect => " + "    " +page);
        List<Info> infoList = infoService.getInfoList(page);

        Map<String, List<Info>> map = new HashMap<>();
        map.put("dataList", infoList);

        Message message = new Message();
        message.setMessage("success");
        message.setStatus(StatusEnum.OK);
        message.setData(map);

        return new ResponseEntity<>(message, responseHeaders, HttpStatus.OK);
    }

    @GetMapping("/info")
    @ResponseBody
    public ResponseEntity<Message> getInfo(@Param("num") Long num){
        Info result = infoService.getInfo(num);

        Message message = new Message();
        Map<String, Info> map = new HashMap<>();

        if (!result.equals(null)) {

            map.put("dataList", result);

            message.setMessage("success");
            message.setStatus(StatusEnum.OK);
            message.setData(map);
        }
        else {
            map.put("dataList", null);

            message.setMessage("fail");
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setData(map);
        }

        return new ResponseEntity<>(message, responseHeaders, HttpStatus.OK);
    }

    @PostMapping("/info")
    @ResponseBody
    public ResponseEntity<Message> setInfo(@RequestBody HashMap<String, Object> param){
        Info info = new Info();
        info.setTitle(param.get("title").toString());
        info.setContent(param.get("content").toString());
        info.setWriter(param.get("writer").toString());
//        info.setTown(param.get("town").toString());

        boolean chk = infoService.setInfo(info);

        Message message = new Message();
        Map<String, Boolean> map = new HashMap<>();

        if(chk){
            map.put("result", true);

            message.setMessage("success");
            message.setStatus(StatusEnum.OK);
            message.setData(map);
        }
        else {
            map.put("result", false);

            message.setMessage("fail");
            message.setStatus(StatusEnum.OK);
            message.setData(map);
        }

        return new ResponseEntity<>(message, responseHeaders, HttpStatus.OK);
    }


}
