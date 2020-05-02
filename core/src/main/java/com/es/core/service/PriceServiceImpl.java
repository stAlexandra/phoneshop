package com.es.core.service;

import com.es.core.dao.PriceDao;
import com.es.core.model.price.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceServiceImpl implements PriceService {
    @Autowired
    private PriceDao priceDao;

    @Override
    public void savePhonePrices(Long phoneId, List<Price> prices) {

    }

    @Override
    public List<Price> getPhonePrices(Long phoneId) {
        return null;
    }
}
