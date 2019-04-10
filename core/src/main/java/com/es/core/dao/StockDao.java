package com.es.core.dao;

import com.es.core.model.stock.Stock;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface StockDao {
    Stock get(Long phoneId);
    List<Stock> getStocks(List<Long> phoneIds);
    void updateStocks(Collection<Stock> stockList);
}