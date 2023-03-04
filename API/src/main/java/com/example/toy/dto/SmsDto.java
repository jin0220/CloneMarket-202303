package com.example.toy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class SmsDto {
    private String to; // 수신번호
    private String content; // 개별 메시지 내용
}
