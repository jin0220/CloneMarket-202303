package com.example.toy.entity.response;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor//(access = AccessLevel.PROTECTED)
@Getter @Setter
public class SmsResponse {
    private String requestId;
    private LocalDateTime requestTime;
    private String statusCode;
    private String statusName;
    private String authNum;
}
