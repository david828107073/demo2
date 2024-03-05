package com.david.controller;

import com.david.common.BasicOut;
import com.david.common.CustomerException;
import com.david.common.ErrorCodeEnum;
import com.david.dto.GoodsDTO;
import com.david.service.GoodsService;
import com.david.vo.GoodsVO;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GoodsController {

    final GoodsService goodsService;


    @GetMapping(value = "/goods")
    public BasicOut<List<GoodsDTO>> findAllGoods(){

    }
    @PostMapping(value = "/goods/add")
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
            result.setMessage(ErrorCodeEnum.SYSTEM_ERROR.getMessage() + " reason:" + e.getMessage());
            result.setRetCode(ErrorCodeEnum.SYSTEM_ERROR.getErrorCode());
        }
        return result;
    }
}
