package com.david.vo;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;

@Data
public class GoodsVO {
    @Parameter(name = "商品名稱", description = "商品名稱", required = true)
    private String goodsName;
}
