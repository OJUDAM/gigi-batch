package com.ujo.gigiScheduler.common.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private String errorCode;

    private ErrorResponse() {
    }

    private ErrorResponse(final String errorCode) {
        this.errorCode = errorCode;
    }

    public static ErrorResponse from(final ErrorCode errorCode) {
        return new ErrorResponse(errorCode.name());
    }
}
