package com.example.toy.entity.response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StatusEnum {
    OK(200, "SUCCESS"),
    BAD_REQUEST(400, "BAD_REQUEST"),
    NOT_FOUND(404, "NOT_FOUND"),
    INTERNAL_SERER_ERROR(500, "INTERNAL_SERER_ERROR");

    int statusCode;
    String code;
}
