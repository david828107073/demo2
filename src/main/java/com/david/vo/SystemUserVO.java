package com.david.vo;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SystemUserVO extends User {
    @Parameter(name = "使用者名稱", description = "使用者名稱", required = true)
    private String name;
}
