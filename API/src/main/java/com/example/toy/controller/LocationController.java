package com.example.toy.controller;

import com.example.toy.dto.LocationDto;
import com.example.toy.entity.response.Message;
import com.example.toy.entity.response.StatusEnum;
import com.example.toy.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class LocationController {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    HttpHeaders responseHeaders;

    private final LocationService locationService;

    @PostConstruct
    public void init() {
        responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json; charset=UTF-8");
    }

    @PostMapping("/location")
    @ResponseBody
    public ResponseEntity<Message> getLocation(@RequestBody HashMap<String, Object> param) {
        Message message = new Message();
        Map<String, List<LocationDto>> map = new HashMap<>();

        List<LocationDto> list = locationService.getLocation(
                Integer.parseInt(param.get("page").toString()),
                param.get("latitude").toString(),
                param.get("longitude").toString()
        );

        if(!list.isEmpty()) {
            log.info("success");
            map.put("dataList", list);

            message.setMessage("success");
            message.setStatus(StatusEnum.OK);
            message.setData(map);
        }
        else {
            map.put("dataList", null);

            message.setMessage("fail");
            message.setStatus(StatusEnum.OK);
            message.setData(map);
        }

        return new ResponseEntity<>(message, responseHeaders, HttpStatus.OK);
    }

}
