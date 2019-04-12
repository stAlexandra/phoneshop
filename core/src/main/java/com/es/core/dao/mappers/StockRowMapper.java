package com.es.core.dao.mappers;

import com.es.core.model.stock.Stock;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class StockRowMapper implements RowMapper<Stock> {
    @Override
    public Stock mapRow(ResultSet resultSet, int i) throws SQLException {
        Stock stock = new Stock();
        stock.setStock(resultSet.getInt("stock"));
        stock.setReserved(resultSet.getInt("reserved"));
        return stock;
    }
}
