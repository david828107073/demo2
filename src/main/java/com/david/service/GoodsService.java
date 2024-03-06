package com.david.service;

import com.david.common.BasicOut;
import com.david.common.CustomerException;
import com.david.common.HttpStatusEnum;
import com.david.dto.GoodsDTO;
import com.david.entity.Goods;
import com.david.entity.SystemUser;
import com.david.repository.GoodsRepository;
import com.david.repository.SystemUserRepository;
import com.david.vo.GoodsVO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoodsService {
    final GoodsRepository goodsRepository;
    final SystemUserRepository systemUserRepository;

    @Transactional(rollbackOn = Exception.class)
    public BasicOut<GoodsDTO> addGoods(GoodsVO goodsVO) throws CustomerException {
        BasicOut result = new BasicOut();
        try {
            Optional<SystemUser> systemUserOptional = systemUserRepository.findByAccount(goodsVO.getAccount());
            SystemUser user = systemUserOptional.orElseThrow(() -> new CustomerException(HttpStatusEnum.ACCOUNT_PASSWORD_ERROR));
            Goods goods = new Goods();
            goods.setName(goodsVO.getGoodsName());
            goods.setCreateUser(user);
            goods.setUpdateUser(user);
            goods.setCreateDateTime(new Timestamp(System.currentTimeMillis()));
            goods.setUpdateDateTime(new Timestamp(System.currentTimeMillis()));
            goodsRepository.save(goods);
            GoodsDTO dto = GoodsDTO.builder().goods_name(goods.getName()).id(goods.getId()).build();
            result.setMessage(HttpStatusEnum.SUCCESS.getMessage());
            result.setRetCode(HttpStatusEnum.SUCCESS.getErrorCode());
            result.setBody(dto);
            return result;
        } catch (CustomerException cu) {
            throw cu;
        } catch (Exception e) {
            throw new CustomerException(HttpStatusEnum.SYSTEM_ERROR);
        }
    }

    public BasicOut<List<GoodsDTO>> findAllGoods() throws CustomerException {
        BasicOut<List<GoodsDTO>> result = new BasicOut<>();
        try {
            List<Goods> goods = goodsRepository.findAll();
            if (CollectionUtils.isEmpty(goods)) {
                result.setMessage("查無資料");
                result.setBody(null);
            } else {
                List<GoodsDTO> goodsDTOs = convert(goods);
                result.setBody(goodsDTOs);
                result.setMessage(HttpStatusEnum.SUCCESS.getMessage());
            }
            result.setRetCode(HttpStatusEnum.SUCCESS.getErrorCode());
            return result;
        } catch (Exception e) {
            throw new CustomerException(HttpStatusEnum.SYSTEM_ERROR, e);
        }
    }

    public BasicOut<GoodsDTO> findById(UUID id) {
        BasicOut<GoodsDTO> result = new BasicOut<>();
        Optional<Goods> goodsOptional = goodsRepository.findById(id);
        result.setRetCode(HttpStatusEnum.SUCCESS.getErrorCode());
        if (goodsOptional.isPresent()) {
            GoodsDTO goodsDTO = convert(goodsOptional.get());
            result.setBody(goodsDTO);
            result.setMessage("查詢成功");
        } else {
            result.setMessage("查無資料");
        }
        return result;
    }

    public BasicOut<Void> deleteById(UUID id) {
        BasicOut<Void> result = new BasicOut<>();
        try {
            Optional<Goods> goodsOptional = goodsRepository.findById(id);
            Goods goods = goodsOptional.orElseThrow(() -> new CustomerException(HttpStatusEnum.GOODS_NOT_FOUND));
            goodsRepository.delete(goods);
            result.setMessage(HttpStatusEnum.SUCCESS.getMessage());
            result.setRetCode(HttpStatusEnum.SUCCESS.getErrorCode());
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            result.setRetCode(HttpStatusEnum.SYSTEM_ERROR.getErrorCode());
        }
        return result;
    }

    private GoodsDTO convert(Goods goods) {
        return GoodsDTO.builder()
                .goods_name(goods.getName())
                .id(goods.getId())
                .build();
    }

    private List<GoodsDTO> convert(List<Goods> goods) {
        if (CollectionUtils.isEmpty(goods)) return Collections.emptyList();
        return goods.stream().map(this::convert).collect(Collectors.toList());
    }

}
