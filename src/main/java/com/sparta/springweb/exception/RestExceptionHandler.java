package com.sparta.springweb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // exception 발생 시 json 형태로 넘겨줌
public class RestExceptionHandler {

// 오류가 IllegalArgumentException 일 때
//    @ExceptionHandler(value = {IllegalArgumentException.class})
//    public ResponseEntity<Posts> handleApiRequestException(IllegalArgumentException e) {
//        RestException restException = new RestException();
//        restException.setHttpStatus(HttpStatus.BAD_REQUEST);
//        restException.setErrorMessage(e.getMessage());
//
//        return new ResponseEntity(
//                // HTTP body
//                restException,
//                // HTTP status code
//                HttpStatus.BAD_REQUEST
//        );
//    }
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected ResponseEntity<RestException> notFoundID(Exception e) {
//    RestException restException = new RestException(ErrorCode.NOT_FOUND_ID); 와 동일
        RestException restException = RestException.of(ErrorCode.NOT_FOUND_POSTID);
        restException.getErrorMessage();
        return new ResponseEntity<>(
                // HTTP body
                restException,
                // HTTP status code
                HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = PostException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected ResponseEntity<RestException> notFoundID(PostException e) {
//    RestException restException = new RestException(ErrorCode.NOT_FOUND_ID); 와 동일
        RestException restException = RestException.of(ErrorCode.NOT_FOUND_USERID);
        restException.getErrorMessage(); // 기존 setHttpStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(
                // HTTP body
                restException,
                // HTTP status code
                HttpStatus.BAD_REQUEST);
    }
}