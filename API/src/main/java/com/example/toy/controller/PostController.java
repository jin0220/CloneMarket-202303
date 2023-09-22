package com.example.toy.controller;

import com.example.toy.entity.Member;
import com.example.toy.entity.Post;
import com.example.toy.entity.response.Message;
import com.example.toy.entity.response.StatusEnum;
import com.example.toy.service.MemberService;
import com.example.toy.service.PostService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.websocket.server.PathParam;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PostController {
    private final PostService postService;
    private final MemberService memberService;

    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    HttpHeaders responseHeaders;

    @Value("${file.upload.path}")
    private String saveDir;


    @PostConstruct
    public void init() {
        responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json; charset=UTF-8");
    }

    @GetMapping("/posts")
    @ResponseBody
    public ResponseEntity<Message> getPost(@Param("page") int page) {
        log.info("connect => " + "    " +page);
        List<Post> postList = postService.getPost();

        Map<String, List<Post>> map = new HashMap<>();
        Message message = new Message();

//        if(!postList.isEmpty()) {
            log.info("success" + postList.get(0).getTime());
            map.put("dataList", postList);

            message.setMessage("success");
            message.setStatus(StatusEnum.OK);
            message.setData(map);
//        }
//        else {
//            log.info("fail");
//            map.put("dataList", null);
//
//            message.setMessage("fail");
//            message.setStatus(StatusEnum.OK);
//            message.setData(map);
//        }

        return new ResponseEntity<>(message, responseHeaders, HttpStatus.OK);
    }

    @PostMapping("/post")
    @ResponseBody
    public ResponseEntity<Message> post(
            @RequestParam("phoneNum") String phoneNum,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("price") String price,
            @RequestParam("date") String date,
            @RequestParam("time") String time,
            @RequestParam("file") MultipartFile file
    ) {

        log.info(title);
        log.info(file.getOriginalFilename());

        Post post = new Post();
        post.setSellerUser(phoneNum);
        post.setTitle(title);
        post.setContent(content);
        post.setPrice(price);
        post.setDate(date);
        post.setTime(time);

        if(!file.isEmpty()){
            updateFile(file);
            post.setImg1(file.getOriginalFilename());
        }


        Member member = memberService.getMemberInfo(phoneNum);

        post.setNickName(member.getNickName());
        post.setProfile(member.getProfile());

        boolean check = postService.setPost(post);

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

    @GetMapping("/postDetail")
    @ResponseBody
    public ResponseEntity<Message> getPostDetail(@Param("postNum") String postNum) {
        log.info("connect getPostDetail => " + "    " +postNum);
        Post post = postService.getPostDetail(Long.parseLong(postNum));

        Map<String, Post> map = new HashMap<>();
        Message message = new Message();

        if(!post.equals(null)) {
            log.info("success" + post);
            map.put("dataList", post);

            message.setMessage("success");
            message.setStatus(StatusEnum.OK);
            message.setData(map);
        }
        else {
            log.info("fail");
            map.put("dataList", null);

            message.setMessage("fail");
            message.setStatus(StatusEnum.OK);
            message.setData(map);
        }

        return new ResponseEntity<>(message, responseHeaders, HttpStatus.OK);
    }

    public void updateFile(MultipartFile file) {
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

}
