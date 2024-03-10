package com.david.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GoodsDTO {
    @Schema(name = "商品 ID", description = "商品 ID")
    private UUID id;
    @Schema(name = "商品名稱", description = "商品名稱")
    private String goods_name;

}
