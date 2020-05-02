package com.es.core.dao.impl;

import com.es.core.dao.StockDao;
import com.es.core.dao.mappers.PhoneRowMapper;
import com.es.core.dao.mappers.StockRowMapper;
import com.es.core.exception.ItemNotFoundException;
import com.es.core.model.phone.Phone;
import com.es.core.model.stock.Stock;
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
    private static final String SQL_GET_STOCK = "SELECT * FROM stocks st JOIN phones ph ON st.phoneId = ph.id " +
            "WHERE st.phoneId = :phoneId";
    private static final String SQL_GET_STOCK_LIST = "SELECT * FROM stocks st JOIN phones ph ON st.phoneId = ph.id " +
            "WHERE st.phoneId IN (:phoneIds)";
    private static final String SQL_UPDATE_STOCK = "UPDATE stocks SET stock = :stock WHERE phoneId = :phoneId";

    @Resource
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Resource
    private StockRowMapper stockRowMapper;

    @Resource
    private PhoneRowMapper phoneRowMapper;

    @Override
    @Transactional(readOnly = true)
    public Stock get(Long phoneId) {
        Map<String, Long> params = Collections.singletonMap("phoneId", phoneId);
        return jdbcTemplate.query(SQL_GET_STOCK, params, resultSet -> {
            if (resultSet.next()) {
                Stock stock = stockRowMapper.mapRow(resultSet, 1);
                stock.setPhone(phoneRowMapper.mapRow(resultSet, 1));
                return stock;
            } else {
                throw new ItemNotFoundException(phoneId.toString());
            }
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<Stock> getStocks(List<Long> phoneIds) {
        MapSqlParameterSource params = new MapSqlParameterSource("phoneIds", phoneIds);
        List<Stock> stockList = new ArrayList<>();
        jdbcTemplate.query(SQL_GET_STOCK_LIST, params, resultSet -> {
            do {
                Stock stock = stockRowMapper.mapRow(resultSet, 1);
                Phone phone = phoneRowMapper.mapRow(resultSet, 1);
                stock.setPhone(phone);
                stockList.add(stock);
            } while (resultSet.next());
        });
        return stockList;
    }

    @Override
    public void updateStocks(Collection<Stock> stockList) {
        SqlParameterSource[] batchParams = stockList.stream().map(stock -> {
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("phoneId", stock.getPhone().getId());
            params.addValue("stock", stock.getStock());
            return params;
        }).toArray(SqlParameterSource[]::new);
        jdbcTemplate.batchUpdate(SQL_UPDATE_STOCK, batchParams);
    }
}
