package com.example.toy.controller;

import com.example.toy.entity.Post;
import com.example.toy.entity.response.Message;
import com.example.toy.entity.response.StatusEnum;
import com.example.toy.service.MemberService;
import com.example.toy.service.PostService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PostController {
    private final PostService postService;

    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    HttpHeaders responseHeaders;


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

        if(!postList.isEmpty()) {
            log.info("success" + postList.get(0).getTime());
            map.put("dataList", postList);

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

    @PostMapping("/post")
    @ResponseBody
    public ResponseEntity<Message> post(@RequestBody HashMap<String, Object> param) {
        log.info(param.get("title").toString());

        Post post = new Post();
        post.setTitle(param.get("title").toString());
        post.setContent(param.get("content").toString());
        post.setPrice(param.get("price").toString());
        post.setDate(param.get("date").toString());
        post.setTime(param.get("time").toString());

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

}
