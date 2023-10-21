package com.example.toy.dto;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class InfoDto {
    private Long num;
    private String writer;
    private String profile;
    private String title;
    private String content;
    private String town;
    private String date;
    private String time;
    private Long viewCnt;
    private Long commentCnt;
    private String nickName;

}
