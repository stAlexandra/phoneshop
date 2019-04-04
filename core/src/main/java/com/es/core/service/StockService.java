package com.es.core.service;

import com.es.core.model.cart.Cart;
import com.es.core.model.stock.Stock;

import java.util.List;
import java.util.Map;

public interface StockService {
    Map<Long, Integer> getPhoneIdToAvailableQuantityMap(Cart cart);

    Map<Long, Stock> getStocks(List<Long> phoneIds);

    void updateStock(Stock stock);
}
