package com.example.toy.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.CustomLog;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "게시글 모델")
@Entity
@Getter @Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_NUM")
    @ApiModelProperty(example = "Post id")
    private Long num;

    @ApiModelProperty(example = "작성자")
    private String sellerUser;

    @ApiModelProperty(example = "작성자 닉네임")
    private String nickName;

    @ApiModelProperty(example = "작성자 프로필")
    private String profile;

    @ApiModelProperty(example = "게시글 제목")
    private String title;

    @ApiModelProperty(example = "게시글 내용")
    private String content;

    @ApiModelProperty(example = "작성날짜")
    private String date;

    @ApiModelProperty(example = "작성시간")
    private String time;

    @ApiModelProperty(example = "판매가격")
    private String price;

    @ApiModelProperty(example = "판매 동네")
    private String location;

    @ApiModelProperty(example = "게시글 사진1")
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
    @ApiModelProperty(example = "채팅 개수")
    private Long chatCnt;

    @ColumnDefault("0")
    @ApiModelProperty(example = "찜한 수")
    private Long likeCnt;

    @ColumnDefault("0")
    @ApiModelProperty(example = "조회수")
    private Long viewCnt;

//    @ManyToOne
//    @JoinColumn(name = "USER_PHONE")
//    private Member member;
//
//    @OneToMany(mappedBy = "post")
//    private List<ChattingRoom> chattingRooms = new ArrayList<>();
}
