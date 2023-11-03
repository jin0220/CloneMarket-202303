package com.example.toy.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChattingRoomDto {
    private Long roomId;
    private String sellerUser;
    private String buyerUser;
    private String sendTime;
    private String contents;
    private String nickName;
    private String profile;

    public ChattingRoomDto(long roomId, String sellerUser, String buyerUser, String sendTime, String contents, String nickName, String profile) {
        this.roomId = roomId;
        this.sellerUser = sellerUser;
        this.buyerUser = buyerUser;
        this.sendTime = sendTime;
        this.contents = contents;
        this.nickName = nickName;
        this.profile = profile;
    }

}
