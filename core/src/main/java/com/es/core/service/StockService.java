package com.es.core.service;

import com.es.core.model.stock.Stock;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface StockService {
    List<Stock> getStocks(List<Long> phoneIds);

    Map<Long, Stock> getStocksMap(List<Long> phoneIds);

    void updateStocks(Collection<Stock> stockList);
}
