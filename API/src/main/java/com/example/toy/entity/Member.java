package com.example.toy.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter @Getter
public class Member {

    @Id
    @Column(name = "USER_PHONE")
    private String phone;
    private String nickName;
    private String profile;
    private String refreshToken;
    private String town;

//    @OneToMany(mappedBy = "member") // 외래키를 가지지 않는 테이블에 사용하여 주인임을 명시
//    List<Post> posts = new ArrayList<>();
}
