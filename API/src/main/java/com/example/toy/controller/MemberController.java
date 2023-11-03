package com.example.toy.controller;

import com.example.toy.configuration.JwtTokenProvider;
import com.example.toy.dto.ImageDto;
import com.example.toy.entity.Member;
import com.example.toy.entity.response.Message;
import com.example.toy.entity.response.StatusEnum;
import com.example.toy.service.AuthService;
import com.example.toy.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberController {

    @Value("${file.upload.path}")
    private String saveDir;

    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    HttpHeaders responseHeaders;

    private final MemberService memberService;
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostConstruct
    public void init() {
        responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json; charset=UTF-8");
    }

    @ApiOperation(value = "회원가입", notes = "")
    @PostMapping("/signup")
    @ResponseBody
    public ResponseEntity<Message> signup(@RequestBody HashMap<String, Object> param) {
        Member member = new Member();
        member.setPhone(param.get("phoneNum").toString());


        Message message = new Message();


        return new ResponseEntity<>(message, responseHeaders, HttpStatus.OK);
    }

    @ApiOperation(value = "로그인", notes = "")
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<Message> login(@RequestBody HashMap<String, Object> param) {
        String phone = param.get("phoneNum").toString();
        boolean check = memberService.login(phone);

        Map<String, String> map = new HashMap<>();
        Message message = new Message();

        if(check) {
            // 1. accessToken과 refreshToken를 생성한다.
            String accessToken = jwtTokenProvider.createToken(phone);
            String refreshToken = jwtTokenProvider.createRefreshToken(phone);

            // 2. refreshToken은 DB에 저장한다. 유효하지 않은 accessToken으로 요청이 왔을 때 처리하기 위한 토근.
            authService.updateRefreshToken(refreshToken, phone);

            log.info("success");
            map.put("result", accessToken);

            message.setMessage("success");
            message.setStatus(StatusEnum.OK);
            message.setData(map);
        }
        else {
            log.info("fail");
            map.put("result", null);

            message.setMessage("fail");
            message.setStatus(StatusEnum.OK);
            message.setData(map);
        }

        return new ResponseEntity<>(message, responseHeaders, HttpStatus.OK);
    }

    @ApiOperation(value = "프로필 설정", notes = "")
    @PostMapping("/profile")
    @ResponseBody
    public ResponseEntity<Message> Profile(
            @RequestParam("phoneNum") String phoneNum,
            @RequestParam("nickName") String nickName,
            @RequestParam("file") MultipartFile file) {
        log.info(phoneNum);
        log.info(nickName);
        log.info(file.getOriginalFilename());

        boolean check;

        if(!file.isEmpty()){
            updateProfile(file);
            check = memberService.profile(phoneNum, nickName, file.getOriginalFilename());
            log.info("profile upload");
        }
        else {
            check = memberService.profile(phoneNum, nickName, null);
            log.info("profile not upload");
        }

        Message message = new Message();
        Map<String, String> map = new HashMap<>();

        if(check) {
            // 1. accessToken과 refreshToken를 생성한다.
            String accessToken = jwtTokenProvider.createToken(phoneNum);
            String refreshToken = jwtTokenProvider.createRefreshToken(phoneNum);

            // 2. refreshToken은 DB에 저장한다. 유효하지 않은 accessToken으로 요청이 왔을 때 처리하기 위한 토근.
            authService.updateRefreshToken(refreshToken, phoneNum);

            log.info("success");
            map.put("result", accessToken);

            message.setMessage("success");
            message.setStatus(StatusEnum.OK);
            message.setData(map);
        }
        else {
            log.info("fail");
            map.put("result", null);

            message.setMessage("fail");
            message.setStatus(StatusEnum.OK);
            message.setData(map);
        }

        return new ResponseEntity<>(message, responseHeaders, HttpStatus.OK);
    }

    public void updateProfile(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();

            File profile = new File(saveDir, fileName);
            file.transferTo(profile);
        }
        catch (IOException e) {
            e.printStackTrace();
            log.info("fail");
        }

    }

    public String getRefreshToken(String userPhone){
        return memberService.getMemberInfo(userPhone).getRefreshToken();
    }

}
