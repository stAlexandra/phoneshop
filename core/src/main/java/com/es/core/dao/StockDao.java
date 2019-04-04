package com.es.core.dao;

import com.es.core.model.stock.Stock;

import java.util.List;
import java.util.Map;

public interface StockDao {
    Stock get(Long phoneId);
    Map<Long, Stock> getStocks(List<Long> phoneIds);
    void updateStock(Stock stock);
}