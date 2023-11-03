package com.example.toy.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "사용자 정보 모델")
@Entity
@Setter @Getter
public class Member {

    @Id
    @Column(name = "USER_PHONE")
    @ApiModelProperty(example = "Member id")
    private String phone;

    @ApiModelProperty(example = "사용자 닉네임")
    private String nickName;

    @ApiModelProperty(example = "사용자 프로필사진")
    private String profile;

    @ApiModelProperty(example = "사용자 리프레시 토큰")
    private String refreshToken;

    @ApiModelProperty(example = "사용자 동네")
    private String town;

//    @OneToMany(mappedBy = "member") // 외래키를 가지지 않는 테이블에 사용하여 주인임을 명시
//    List<Post> posts = new ArrayList<>();
}
