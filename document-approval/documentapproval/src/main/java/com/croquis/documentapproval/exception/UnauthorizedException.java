package com.croquis.documentapproval.exception;

//TODO Exception 구체화
public class UnauthorizedException extends RuntimeException {
    private final ErrorCode errorCode;

    public UnauthorizedException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
