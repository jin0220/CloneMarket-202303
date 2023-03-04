package com.example.toy.entity.request;

import com.example.toy.dto.SmsDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter @Setter
public class SmsRequest {
    private String type; // sms type (SMS, LMS, MMS)
    private String contentType; // 메시지 Type (COMM: 일반메시지, AD: 광고메시지, default: COMM)
    private String countryCode; // 국가 번호
    private String from; // 발신번호
    private String content; // 기본 메시지 내용
    private SmsDto message; //메시지 정보
}
