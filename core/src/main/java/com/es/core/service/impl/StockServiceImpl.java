package com.es.core.service.impl;

import com.es.core.dao.StockDao;
import com.es.core.model.cart.Cart;
import com.es.core.model.stock.Stock;
import com.es.core.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StockServiceImpl implements StockService {
    private StockDao stockDao;

    @Autowired
    public StockServiceImpl(StockDao stockDao) {
        this.stockDao = stockDao;
    }

    @Override
    public Map<Long, Integer> getPhoneIdToAvailableQuantityMap(Cart cart) {
        List<Long> phoneIdList = cart.getItems().stream().map(item -> item.getPhone().getId()).collect(Collectors.toList());

        return stockDao.getStocks(phoneIdList).entrySet().stream()
                .collect(Collectors.toMap(Map.Entry<Long, Stock>::getKey,
                        entrySet -> entrySet.getValue().getStock() - entrySet.getValue().getReserved()));
    }

    @Override
    public Map<Long, Stock> getStocks(List<Long> phoneIds) {
        return stockDao.getStocks(phoneIds);
    }

    @Override
    public void updateStock(Stock stock) {
        stockDao.updateStock(stock);
    }

}
