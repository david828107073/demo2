package com.david.common;

public class CustomerException extends RuntimeException {

    public CustomerException(HttpStatusEnum errorCodeEnum) {
        super(errorCodeEnum.getErrorCode() + " : " + errorCodeEnum.getMessage());
    }

    public CustomerException(String message) {
        super(message);
    }

    public CustomerException(HttpStatusEnum errorCodeEnum, Exception e) {
        super(e);
    }
}
