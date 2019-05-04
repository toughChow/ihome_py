package com.ctbu.guesthouse.dao;

import com.ctbu.guesthouse.domain.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsDao extends JpaRepository<Goods, Long> {
}
