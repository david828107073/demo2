package com.david.controller;

import com.david.common.BasicOut;
import com.david.common.CustomerException;
import com.david.common.HttpStatusEnum;
import com.david.dto.GoodsDTO;
import com.david.service.GoodsService;
import com.david.vo.GoodsVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "商品 API", description = "商品增刪改查")
public class GoodsController {

    final GoodsService goodsService;


    @GetMapping(value = "/goods")
    @Operation(summary = "查詢所有商品", security = {@SecurityRequirement(name = "api token")})
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "成功",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = GoodsDTO.class))
                            )),
                    @ApiResponse(responseCode = "403", description = "權限不足", content = @Content()),
                    @ApiResponse(responseCode = "500", description = "系統錯誤", content = @Content())
            }
    )
    public BasicOut<List<GoodsDTO>> findAllGoods() {
        BasicOut<List<GoodsDTO>> result = new BasicOut<>();
        try {
            List<GoodsDTO> goodsList = goodsService.findAllGoods();
            result.setRetCode(HttpStatusEnum.SUCCESS.getCode());
            if (!CollectionUtils.isEmpty(goodsList)) {
                result.setBody(goodsList);
                result.setMessage(HttpStatusEnum.SUCCESS.getMessage());

            } else {
                result.setMessage("查無資料");
            }
        } catch (Exception e) {
            getErrResponse(e, result);
        }
        return result;
    }

    @GetMapping(value = "/goods/{id}")
    @Operation(summary = "依商品編號查詢商品", security = {@SecurityRequirement(name = "api token")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GoodsDTO.class))),
            @ApiResponse(responseCode = "403", description = "權限不足", content = @Content()),
            @ApiResponse(responseCode = "500", description = "系統錯誤", content = @Content())
    })
    public BasicOut<GoodsDTO> findById(@Parameter(name = "商品編號", required =
            true, description = "商品編號") @PathVariable(name = "id") UUID id) {
        BasicOut<GoodsDTO> result = new BasicOut<>();
        try {
            GoodsDTO goodsDTO = goodsService.findById(id);
            result.setRetCode(HttpStatusEnum.SUCCESS.getCode());
            if (!ObjectUtils.isEmpty(goodsDTO)) {
                result.setBody(goodsDTO);
                result.setMessage(HttpStatusEnum.SUCCESS.getMessage());
            } else {
                result.setMessage("查無資料");
            }
        } catch (Exception e) {
            getErrResponse(e, result);
        }
        return result;
    }

    @PostMapping(value = "/goods/add")
    @Operation(summary = "新增商品", security = {@SecurityRequirement(name = "api token")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "成功",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GoodsDTO.class)
                    )),
            @ApiResponse(responseCode = "401", description = "帳號密碼錯誤", content = @Content()),
            @ApiResponse(responseCode = "403", description = "權限不足", content = @Content()),
            @ApiResponse(responseCode = "500", description = "系統錯誤", content = @Content())
    })
    public BasicOut<GoodsDTO> addGoods(@RequestBody GoodsVO goodsVO) {
        BasicOut<GoodsDTO> result = new BasicOut<>();
        try {
            if (!StringUtils.hasText(goodsVO.getGoodsName())) {
                throw new CustomerException(HttpStatusEnum.INPUT_VALUE_VALIDATE_ERROR, "請輸入商品名稱");
            }
            GoodsDTO goodsDTO = goodsService.addGoods(goodsVO);
            result.setRetCode(HttpStatusEnum.SUCCESS.getCode());
            result.setMessage(HttpStatusEnum.SUCCESS.getMessage());
            result.setBody(goodsDTO);
        } catch (Exception e) {
            getErrResponse(e, result);
        }
        return result;
    }

    @PutMapping(value = "/goods/{id}")
    @Operation(summary = "更新商品", security = {@SecurityRequirement(name = "api token")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "成功",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GoodsDTO.class)
                    )),
            @ApiResponse(responseCode = "401", description = "帳號密碼有誤", content = @Content()),
            @ApiResponse(responseCode = "403", description = "權限不足", content = @Content()),
            @ApiResponse(responseCode = "500", description = "系統錯誤", content = @Content()),
            @ApiResponse(responseCode = "E001", description = "商品不存在", content = @Content()),
            @ApiResponse(responseCode = "E002", description = "參數檢核有誤", content = @Content())
    })
    public BasicOut<GoodsDTO> updateGoods(@Parameter(name = "商品 ID", description = "商品 ID", required = true) @PathVariable("id") UUID id, @RequestBody GoodsVO goodsVO) {
        BasicOut<GoodsDTO> result = new BasicOut<>();
        try {
            if (ObjectUtils.isEmpty(id)) {
                throw new CustomerException(HttpStatusEnum.INPUT_VALUE_VALIDATE_ERROR, "請輸入商品序號");
            }
            if (ObjectUtils.isEmpty(goodsVO) || !StringUtils.hasText(goodsVO.getGoodsName())) {
                throw new CustomerException(HttpStatusEnum.INPUT_VALUE_VALIDATE_ERROR, "請輸入商品名稱");
            }
            GoodsDTO goodsDTO = goodsService.updateById(id, goodsVO);
            result.setRetCode(HttpStatusEnum.SUCCESS.getCode());
            result.setMessage(HttpStatusEnum.SUCCESS.getMessage());
            result.setBody(goodsDTO);

        } catch (Exception e) {
            getErrResponse(e, result);
        }
        return result;
    }

    @DeleteMapping(value = "/goods/{id}")
    @Operation(security = {@SecurityRequirement(name = "api token")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功",
                    content = @Content()),
            @ApiResponse(responseCode = "500", description = "系統錯誤", content = @Content()),
            @ApiResponse(responseCode = "E001", description = "商品不存在", content = @Content())
    })
    public BasicOut<Void> deleteGoods(@Parameter(name = "商品 ID", description = "商品 ID", required = true) @PathVariable(name = "id") UUID id) {
        BasicOut<Void> result = new BasicOut<>();
        try {
            goodsService.deleteById(id);
            result.setMessage(HttpStatusEnum.SUCCESS.getMessage());
            result.setRetCode(HttpStatusEnum.SUCCESS.getCode());
        } catch (Exception e) {
            getErrResponse(e, result);
        }
        return result;
    }

    /**
     * 異常統一回傳處理
     *
     * @param e
     * @param result
     */
    private void getErrResponse(Exception e, BasicOut<?> result) {
        if (e instanceof CustomerException c) {
            result.setRetCode(c.getErrorCode());
            result.setMessage(c.getMessage());
        } else {
            result.setMessage(HttpStatusEnum.SYSTEM_ERROR.getMessage() + " reason: " + e.getLocalizedMessage());
            result.setRetCode(HttpStatusEnum.SYSTEM_ERROR.getCode());
        }
    }


}
