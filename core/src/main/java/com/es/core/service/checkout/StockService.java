package com.es.core.service.checkout;

import com.es.core.model.stock.Stock;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface StockService {
    List<Stock> getStocks(List<Long> productIds);

    Map<Long, Stock> getStocksMap(List<Long> productIds);

    void updateStocks(Collection<Stock> stockList);
}
