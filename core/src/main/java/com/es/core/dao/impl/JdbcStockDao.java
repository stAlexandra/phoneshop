package com.es.core.dao.impl;

import com.es.core.dao.StockDao;
import com.es.core.dao.mappers.StockRowMapper;
import com.es.core.exception.ItemNotFoundException;
import com.es.core.model.product.Product;
import com.es.core.model.stock.Stock;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Transactional
@Repository
public class JdbcStockDao implements StockDao {
    private static final String SQL_GET_STOCK = "SELECT * FROM stocks st JOIN books b ON st.productId = b.id " +
            "WHERE st.productId = :productId";
    private static final String SQL_GET_STOCK_LIST = "SELECT * FROM stocks st JOIN books b ON st.productId = b.id " +
            "WHERE st.productId IN (:productIds)";
    private static final String SQL_UPDATE_STOCK = "UPDATE stocks SET stock = :stock WHERE productId = :productId";

    @Resource
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Resource
    private StockRowMapper stockRowMapper;

    @Resource
    private RowMapper<Product> productRowMapper;

    @Override
    @Transactional(readOnly = true)
    public Stock get(Long productId) {
        Map<String, Long> params = Collections.singletonMap("productId", productId);
        return jdbcTemplate.query(SQL_GET_STOCK, params, resultSet -> {
            if (resultSet.next()) {
                Stock stock = stockRowMapper.mapRow(resultSet, 1);
                stock.setProduct(productRowMapper.mapRow(resultSet, 1));
                return stock;
            } else {
                throw new ItemNotFoundException(productId.toString());
            }
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<Stock> getStocks(List<Long> productIds) {
        MapSqlParameterSource params = new MapSqlParameterSource("productIds", productIds);
        List<Stock> stockList = new ArrayList<>();
        jdbcTemplate.query(SQL_GET_STOCK_LIST, params, resultSet -> {
            do {
                Stock stock = stockRowMapper.mapRow(resultSet, 1);
                Product product = productRowMapper.mapRow(resultSet, 1);
                stock.setProduct(product);
                stockList.add(stock);
            } while (resultSet.next());
        });
        return stockList;
    }

    @Override
    public void updateStocks(Collection<Stock> stockList) {
        SqlParameterSource[] batchParams = stockList.stream().map(stock -> {
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("productId", stock.getProduct().getId());
            params.addValue("stock", stock.getStock());
            return params;
        }).toArray(SqlParameterSource[]::new);
        jdbcTemplate.batchUpdate(SQL_UPDATE_STOCK, batchParams);
    }
}
