package com.david.vo;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;

@Data
public class SystemUserVO {
    @Parameter(name = "帳號", description = "帳號", required = true)
    private String account;
    @Parameter(name = "密碼", description = "密碼", required = true)
    private String password;
    @Parameter(name = "使用者名稱", description = "使用者名稱", required = true)
    private String name;
}
