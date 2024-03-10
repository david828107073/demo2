package com.david.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SystemUserDTO {
    @Schema(name = "使用者名稱", description = "使用者名稱")
    private String username;
    @Schema(name = "帳號", description = "帳號")
    private String account;
}
