package com.example.toy.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Info {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long num;
    private String writer;
    private String profile;
    private String title;
    private String content;
    private String town;
    private String date;
    private String time;

    @ColumnDefault("0")
    private Long viewCnt;
    @ColumnDefault("0")
    private Long commentCnt;
}
