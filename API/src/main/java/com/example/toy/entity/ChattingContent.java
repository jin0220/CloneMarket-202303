package com.example.toy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter @Setter
public class ChattingContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long num;
    private Long roomId;
    private String userPhone;
    private String contents;
    private String sendTime;

//    @JsonIgnore // json으로 데이터 만들때 제외시킴.
//    @ManyToOne
//    @JoinColumn(name = "ROOM_ID")
//    private ChattingRoom chattingRoom;
}
