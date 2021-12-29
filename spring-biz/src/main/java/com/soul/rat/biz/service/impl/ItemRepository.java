package com.soul.rat.biz.service.impl;

import com.soul.rat.biz.dto.ItemDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author zhujx
 */
public interface ItemRepository extends ElasticsearchRepository<ItemDTO, Long> {

    /**
     * 根据价格区间查询
     *
     * @param price1
     * @param price2
     * @return
     */
    List<ItemDTO> findByPriceBetween(double price1, double price2);
}
