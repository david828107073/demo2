package com.david.controller;

import com.david.common.BasicOut;
import com.david.common.CustomerException;
import com.david.common.HttpStatusEnum;
import com.david.dto.GoodsDTO;
import com.david.service.GoodsService;
import com.david.vo.GoodsVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class GoodsController {

    final GoodsService goodsService;


    @GetMapping(value = "/goods")
    @Operation(security = {@SecurityRequirement(name = "api token")})
    public BasicOut<List<GoodsDTO>> findAllGoods() {
        BasicOut<List<GoodsDTO>> result = new BasicOut<>();
        try {
            result = goodsService.findAllGoods();
        } catch (Exception e) {
            result.setMessage(HttpStatusEnum.SYSTEM_ERROR.getMessage() + " reason: " + e.getMessage());
            result.setRetCode(HttpStatusEnum.SYSTEM_ERROR.getErrorCode());
        }
        return result;
    }

    @GetMapping(value = "/goods/{id}")
    @Operation(security = {@SecurityRequirement(name = "api token")})
    public BasicOut<GoodsDTO> findById(@PathVariable(name = "id") UUID id) {
        BasicOut<GoodsDTO> result = new BasicOut<>();
        try {
            result = goodsService.findById(id);
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            result.setRetCode(HttpStatusEnum.SYSTEM_ERROR.getErrorCode());
            result.setBody(null);
        }
        return result;
    }

    @PostMapping(value = "/goods/add")
    @Operation(security = {@SecurityRequirement(name = "api token")})
    public BasicOut<GoodsDTO> addGoods(@RequestBody GoodsVO goodsVO) {
        BasicOut<GoodsDTO> result = new BasicOut<>();
        try {
            if (!StringUtils.hasText(goodsVO.getGoodsName())) {
                throw new CustomerException("請輸入商品名稱");
            }
            //TODO 取出 account 塞入 goodsVO
            result = goodsService.addGoods(goodsVO);
        } catch (CustomerException e) {
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            result.setMessage(HttpStatusEnum.SYSTEM_ERROR.getMessage() + " reason: " + e.getMessage());
            result.setRetCode(HttpStatusEnum.SYSTEM_ERROR.getErrorCode());
        }
        return result;
    }

    @DeleteMapping(value = "/goods/{id}")
    @Operation(security = {@SecurityRequirement(name = "api token")})
    public BasicOut<Void> deleteGoods(@PathVariable(name = "id") UUID id) {
        BasicOut<Void> result = new BasicOut<>();
        try {
            result = goodsService.deleteById(id);
        } catch (Exception e) {
            result.setRetCode(HttpStatusEnum.SYSTEM_ERROR.getErrorCode());
            result.setMessage(e.getMessage());
        }
        return result;
    }


}
