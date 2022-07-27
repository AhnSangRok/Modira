package com.sparta.springweb.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
// 동일한 포맷으로 Error를 return하기 위한 class
public class RestException {
    private String errorMessage;
    private HttpStatus httpStatus;
    private String errorcode;

    public RestException(ErrorCode errorCode) {
        this.errorMessage = errorCode.getErrorMessage();
        this.httpStatus = errorCode.getHttpStatus();
        this.errorcode = errorCode.getErrorCode();
    }

    public static RestException of(ErrorCode errorCode) {
        return new RestException(errorCode);
    }
}
