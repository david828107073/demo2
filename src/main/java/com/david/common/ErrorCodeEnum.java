package com.david.common;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
public enum ErrorCodeEnum {

    ACCOUNT_PASSWORD_ERROR(401, "帳號密碼錯誤"),
    SYSTEM_ERROR(500, "系統錯誤"),
    PERMISSION_DENIED(403, "權限不足"),
    SUCCESS(200, "成功");
    private final int errorCode;
    private final String message;

}
