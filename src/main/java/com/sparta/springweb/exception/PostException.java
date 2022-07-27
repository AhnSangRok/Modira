package com.sparta.springweb.exception;

// 비지니스로직 상 발생 시키고 싶은 경우(=Rollback 이 필요)  RuntimeException 을 상속받아서 구현
public class PostException extends RuntimeException{
    private ErrorCode errorCode;

    public PostException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public PostException(ErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }
}
