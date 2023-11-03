package com.example.toy.controller;

import com.example.toy.dto.InfoDto;
import com.example.toy.entity.Info;
import com.example.toy.entity.response.Message;
import com.example.toy.entity.response.StatusEnum;
import com.example.toy.service.InfoService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
import javax.websocket.server.PathParam;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    @ApiOperation(value = "동네생활 조회", notes = "동네생활 전체 리스트를 조회한다.")
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

    @ApiOperation(value = "동네생활의 상세정보 조회", notes = "동네생활 게시글의 상세 정보를 조회한다.")
    @GetMapping("/info")
    @ResponseBody
    public ResponseEntity<Message> getInfo(@Param("userPhone") String userPhone, @Param("num") Long num){
        InfoDto result = infoService.getInfo(userPhone, num);

        Message message = new Message();
        Map<String, InfoDto> map = new HashMap<>();

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

    @ApiOperation(value = "게시글 생성", notes = "사용자의 입력 정보를 생성한다.")
    @ApiImplicitParams({
        @ApiImplicitParam(
                name = "title"
                , value = "게시글 제목"
                , required = true
                , dataType = "string"
                , paramType = "body"
        )
        ,
        @ApiImplicitParam(
                name = "content"
                , value = "게시글 내용"
                , required = true
                , dataType = "string"
                , paramType = "body"
        ),
            @ApiImplicitParam(
                    name = "writer"
                    , value = "작성자 아이디"
                    , required = true
                    , dataType = "string"
                    , paramType = "body"
            )
    })
    @PostMapping("/info")
    @ResponseBody
    public ResponseEntity<Message> setInfo(@RequestBody HashMap<String, Object> param){
        Info info = new Info();
        info.setTitle(param.get("title").toString());
        info.setContent(param.get("content").toString());
        info.setWriter(param.get("writer").toString());
//        info.setTown(param.get("town").toString());

        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat time = new SimpleDateFormat("HH:mm");
        Date today = new Date(System.currentTimeMillis());

        info.setDate(date.format(today));
        info.setTime(time.format(today));

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

    @ApiOperation(value = "게시글 삭제", notes = "사용자의 게시글을 삭제한다.")
    @DeleteMapping("/info/{num}")
    public ResponseEntity<Message> deleteInfo(@PathVariable Long num) {
        boolean chk = infoService.deleteInfo(num);

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

    @ApiOperation(value = "게시글 수정", notes = "사용자의 게시글을 수정한다.")
    @PutMapping("/info/{num}")
    public ResponseEntity<Message> modInfo(
            @PathVariable Long num,
            @RequestBody HashMap<String, Object> param
    ) {
        String title = param.get("title").toString();
        String content = param.get("content").toString();

        boolean chk = infoService.modInfo(num,title,content);

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
