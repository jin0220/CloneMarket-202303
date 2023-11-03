package com.example.toy.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@ApiModel(description = "동네생활 게시글 모델")
@Entity
@Getter @Setter
public class Info {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(example = "Info id")
    private Long num;

    @ApiModelProperty(example = "작성자")
    private String writer;

    @ApiModelProperty(example = "작성자의 프로필")
    private String profile;

    @ApiModelProperty(example = "게시글 제목")
    private String title;

    @ApiModelProperty(example = "게시글 내용")
    private String content;

    @ApiModelProperty(example = "작성자의 동네")
    private String town;

    @ApiModelProperty(example = "작성날짜")
    private String date;

    @ApiModelProperty(example = "작성시간")
    private String time;

    @Column(name = "view_cnt")
    @ColumnDefault("0")
    @ApiModelProperty(example = "조회수")
    private Long viewCnt;

    @Column(name = "comment_cnt")
    @ColumnDefault("0")
    @ApiModelProperty(example = "댓글수")
    private Long commentCnt;
}
