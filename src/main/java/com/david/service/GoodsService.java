package com.david.service;

import com.david.common.CustomerException;
import com.david.common.HttpStatusEnum;
import com.david.dto.GoodsDTO;
import com.david.entity.Goods;
import com.david.entity.SystemUser;
import com.david.repository.GoodsRepository;
import com.david.repository.SystemUserRepository;
import com.david.vo.GoodsVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoodsService {
    final GoodsRepository goodsRepository;
    final SystemUserRepository systemUserRepository;

    @Transactional
    public GoodsDTO addGoods(GoodsVO goodsVO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String account = authentication.getName();
        SystemUser systemUser = systemUserRepository.findByAccount(account).orElseThrow(() -> new CustomerException(HttpStatusEnum.ACCOUNT_PASSWORD_ERROR));
        Goods goods = new Goods();
        goods.setName(goodsVO.getGoodsName());
        goods.setCreateUser(systemUser);
        goods.setUpdateUser(systemUser);
        goods.setCreateDateTime(new Timestamp(System.currentTimeMillis()));
        goods.setUpdateDateTime(new Timestamp(System.currentTimeMillis()));
        goods = goodsRepository.save(goods);
        return convert(goods);
    }

    public List<GoodsDTO> findAllGoods() {
        List<Goods> goods = goodsRepository.findAll();
        return convert(goods);
    }

    public GoodsDTO findById(UUID id) {
        Goods goods = goodsRepository.findById(id).orElseThrow(() -> new CustomerException(HttpStatusEnum.GOODS_NOT_FOUND));
        return convert(goods);
    }

    @Transactional
    public void deleteById(UUID id) {
        Goods goods = goodsRepository.findById(id).orElseThrow(() -> new CustomerException(HttpStatusEnum.GOODS_NOT_FOUND));
        goodsRepository.delete(goods);
    }

    @Transactional
    public GoodsDTO updateById(UUID id, GoodsVO goodsVO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String account = authentication.getName();
        //查無帳號 throw CustomerException
        SystemUser systemUser = systemUserRepository.findByAccount(account).orElseThrow(() -> new CustomerException(HttpStatusEnum.ACCOUNT_PASSWORD_ERROR));
        // 查無商品 throw CustomerException
        Goods goods = goodsRepository.findById(id).orElseThrow(() -> new CustomerException(HttpStatusEnum.GOODS_NOT_FOUND));
        goods.setUpdateDateTime(new Timestamp(System.currentTimeMillis()));
        goods.setUpdateUser(systemUser);
        goods.setName(goodsVO.getGoodsName());
        goods = goodsRepository.save(goods);
        return convert(goods);
    }

    /**
     * Entity 轉 DTO
     *
     * @param goods
     * @return
     */
    private GoodsDTO convert(Goods goods) {
        return GoodsDTO.builder()
                .goods_name(goods.getName())
                .id(goods.getId())
                .build();
    }

    /**
     * Entities 轉 DTOs
     *
     * @param goods
     * @return
     */

    private List<GoodsDTO> convert(List<Goods> goods) {
        if (CollectionUtils.isEmpty(goods)) return Collections.emptyList();
        return goods.stream().map(this::convert).collect(Collectors.toList());
    }

}
