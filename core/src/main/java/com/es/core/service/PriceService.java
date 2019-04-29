package com.es.core.service;

import com.es.core.model.price.Price;

import java.util.List;

public interface PriceService {
    void savePhonePrices(Long phoneId, List<Price> prices);
    List<Price> getPhonePrices(Long phoneId);
}
