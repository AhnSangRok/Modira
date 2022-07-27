package com.sparta.springweb.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
// EnumModel Interface를 구현한 ErrorCode class
public enum ErrorCode implements EnumModle{
    // 400 Bad Request
    DUPLICATED_FOLDER_NAME(HttpStatus.BAD_REQUEST, "400_1", "중복폴더명이 이미 존재합니다."),
    BELOW_MIN_MY_PRICE(HttpStatus.BAD_REQUEST, "400_2", "최저 희망가는 최소 원 이상으로 설정해 주세요."),

    // 404 Not Found
    NOT_FOUND_POSTID(HttpStatus.NOT_FOUND, "404_1", "해당 포스트 아이디가 존재하지 않습니다."),
    NOT_FOUND_USERID(HttpStatus.NOT_FOUND, "404_2", "해당 포스트를 작성한 유저가 아닙니다."),
    ;

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String errorMessage;

    ErrorCode(HttpStatus httpStatus, String errorCode, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getKey() {
        return this.errorCode;
    }

    public String getValue() {
        return this.errorMessage;
    }
}
