package com.es.core.dao;

import com.es.core.model.price.Price;

import java.util.List;

public interface PriceDao {
    List<Price> getByPhoneId(Long phoneId);
    void save(Long phoneId, Price price);
    void saveAll(Long phoneId, List<Price> prices);
}
