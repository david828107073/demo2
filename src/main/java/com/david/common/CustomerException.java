package com.david.common;

public class CustomerException extends Exception {

    public CustomerException(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getErrorCode() + " : " + errorCodeEnum.getMessage());
    }

    public CustomerException(String message) {
        super(message);
    }

    public CustomerException(ErrorCodeEnum errorCodeEnum,Exception e) {
        super(e);
    }
}