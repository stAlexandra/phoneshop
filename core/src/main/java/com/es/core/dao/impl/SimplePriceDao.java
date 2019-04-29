package com.es.core.dao.impl;

import com.es.core.dao.PriceDao;
import com.es.core.model.price.Price;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SimplePriceDao implements PriceDao {
    private Map<Long, List<Price>> phoneIdToPrices;

    public SimplePriceDao() {
        this.phoneIdToPrices = new HashMap<>();
        //phoneIdToPrices.put()
    }

    @Override
    public List<Price> getByPhoneId(Long phoneId) {
        return null;
    }

    @Override
    public void save(Long phoneId, Price price) {

    }
}
