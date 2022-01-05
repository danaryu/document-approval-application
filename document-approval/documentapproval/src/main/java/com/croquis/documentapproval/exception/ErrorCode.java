package com.croquis.documentapproval.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    INVALID_REQUEST("E0001","잘못된 요청입니다.");

    private String errorCode;
    private String message;

    ErrorCode(String errorCode, String message) {
        this.message = message;
        this.errorCode = errorCode;
    }
}
