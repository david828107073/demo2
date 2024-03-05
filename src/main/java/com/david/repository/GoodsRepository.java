package com.david.repository;

import com.david.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GoodsRepository extends JpaRepository<Goods, UUID> {
}
