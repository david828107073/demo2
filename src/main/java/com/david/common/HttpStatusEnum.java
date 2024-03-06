package com.david.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum HttpStatusEnum {

    ACCOUNT_PASSWORD_ERROR("401", "帳號密碼錯誤"),
    SYSTEM_ERROR("500", "系統錯誤"),
    PERMISSION_DENIED("403", "權限不足"),
    SUCCESS("200", "成功"),
    GOODS_NOT_FOUND("E001","商品不存在");
    private final String errorCode;
    private final String message;

}
