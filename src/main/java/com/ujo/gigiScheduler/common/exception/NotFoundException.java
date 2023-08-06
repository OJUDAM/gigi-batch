package com.ujo.gigiScheduler.common.exception;

public class NotFoundException extends CustomException {
    public NotFoundException(final String message, final ErrorCode errorCode) {
        super(message, errorCode);
    }
}
