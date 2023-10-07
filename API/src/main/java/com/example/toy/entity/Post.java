package com.example.toy.entity;

import lombok.CustomLog;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_NUM")
    private Long num;
    private String sellerUser;
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

    @ColumnDefault("0")
    private Long chatCnt;
    @ColumnDefault("0")
    private Long likeCnt;
    @ColumnDefault("0")
    private Long viewCnt;

//    @ManyToOne
//    @JoinColumn(name = "USER_PHONE")
//    private Member member;
//
//    @OneToMany(mappedBy = "post")
//    private List<ChattingRoom> chattingRooms = new ArrayList<>();
}
