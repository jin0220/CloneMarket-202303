package com.example.toy.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Setter @Getter
public class Member {

    @Id
    private String phone;
    private String nickName;
    private String profile;
    private String refreshToken;
}
