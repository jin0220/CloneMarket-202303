package com.example.toy.controller;

import com.example.toy.entity.response.Message;
import com.example.toy.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.HashMap;

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

        locationService.getLocation(Double.parseDouble(param.get("latitude").toString()),
                Double.parseDouble(param.get("longitude").toString()));

        return new ResponseEntity<>(message, responseHeaders, HttpStatus.OK);
    }

}
