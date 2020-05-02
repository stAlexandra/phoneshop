package com.es.core.service.checkout;

import com.es.core.dao.StockDao;
import com.es.core.model.stock.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

@Service
public class StockServiceImpl implements StockService {
    private StockDao stockDao;

    @Autowired
    public StockServiceImpl(StockDao stockDao) {
        this.stockDao = stockDao;
    }

    @Override
    public List<Stock> getStocks(List<Long> phoneIds) {
        return stockDao.getStocks(phoneIds);
    }

    @Override
    public Map<Long, Stock> getStocksMap(List<Long> phoneIds) {
        return stockDao.getStocks(phoneIds).stream()
                .collect(Collectors.toMap(stock -> stock.getPhone().getId(), UnaryOperator.identity()));
    }

    @Override
    public void updateStocks(Collection<Stock> stockList) {
        stockDao.updateStocks(stockList);
    }

}
