package com.david.common;

public class CustomerException extends Exception {

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
