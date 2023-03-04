package com.example.toy.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Setter @Getter
public class Member {
    String  phoneNum;
    String  authNo;
}
