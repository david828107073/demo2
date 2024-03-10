package com.david.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 回傳格式代碼
 */
@RequiredArgsConstructor
@Getter
public enum HttpStatusEnum {

    ACCOUNT_PASSWORD_ERROR("401", "帳號密碼錯誤"),
    SYSTEM_ERROR("500", "系統錯誤"),
    PERMISSION_DENIED("403", "權限不足"),
    SUCCESS("200", "成功"),
    GOODS_NOT_FOUND("E001", "商品不存在"),
    INPUT_VALUE_VALIDATE_ERROR("E002", "參數檢核有誤"),
    USER_EXIST_ERROR("E003", "帳號已存在"),
    UNKNOWN_ERROR("E999", "未知異常");
    private final String code;
    private final String message;

}
