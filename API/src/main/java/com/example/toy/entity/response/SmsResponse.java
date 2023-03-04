package com.example.toy.entity.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SmsResponse {
    private String requestId;
    private LocalDateTime requestTime;
    private String statusCode;
    private String statusName;
}
