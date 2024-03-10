package com.david.common;

import lombok.Getter;

@Getter
public class CustomerException extends RuntimeException {
    private final String errorCode;
    private final String errorMessage;

    public CustomerException(HttpStatusEnum errorCodeEnum) {
        super(errorCodeEnum.getCode() + " : " + errorCodeEnum.getMessage());
        this.errorCode = errorCodeEnum.getCode();
        this.errorMessage = errorCodeEnum.getMessage();
    }

    public CustomerException(HttpStatusEnum errorCodeEnum, String message) {
        super(message);
        this.errorCode = errorCodeEnum.getCode();
        this.errorMessage = errorCodeEnum.getMessage() + " reason: " + message;
    }

    public CustomerException(HttpStatusEnum errorCodeEnum, Exception e) {
        super(e);
        this.errorMessage = errorCodeEnum.getMessage();
        this.errorCode = errorCodeEnum.getCode();
    }
}
