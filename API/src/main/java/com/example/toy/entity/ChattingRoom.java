package com.example.toy.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;


@Entity
@Getter @Setter
public class ChattingRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;
    private Long postNum;
    private String sellerUser;
    private String buyerUser;
    private Date createTime;
    private Date updateTime;

}
