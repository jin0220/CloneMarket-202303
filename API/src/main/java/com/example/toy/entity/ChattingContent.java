package com.example.toy.entity;

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
    private Long roodId;
    private String userPhone;
    private String contents;
    private Date sendTime;
}
