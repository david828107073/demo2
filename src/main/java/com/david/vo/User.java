package com.david.vo;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;

@Data
public abstract class User {
    @Parameter(name = "帳號", description = "帳號", required = true)
    private String account;
    @Parameter(name = "密碼", description = "密碼", required = true)
    private String password;
}
