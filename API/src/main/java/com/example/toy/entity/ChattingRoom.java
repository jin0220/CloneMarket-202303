package com.example.toy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter @Setter
public class ChattingRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROOM_ID")
    private Long roomId;
    private Long postNum;
    private String sellerUser;
    private String buyerUser;
    private String createTime;
    private String updateTime;

//    @ManyToOne
//    @JoinColumn(name = "POST_NUM")
//    private Post post;

//    @JsonIgnore
//    @OneToMany(mappedBy = "chattingRoom")
//    List<ChattingContent> chattingContents = new ArrayList<>();

}
