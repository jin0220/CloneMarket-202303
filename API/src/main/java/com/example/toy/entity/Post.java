package com.example.toy.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long num;
    private String phoneNum;
    private String nickName;
    private String profile;
    private String title;
    private String content;
    private String date;
    private String time;
    private String price;
    private String location;
    private String img1;
    private String img2;
    private String img3;
    private String img4;
    private String img5;
    private String img6;
    private String img7;
    private String img8;
    private String img9;
    private String img10;
}
