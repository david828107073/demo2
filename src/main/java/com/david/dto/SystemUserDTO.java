package com.david.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SystemUserDTO {
    private String username;
    private String account;
}
