package com.es.core.dao.impl;

import com.es.core.model.stock.Stock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:context/applicationContext-core.xml")
@Sql("classpath:db/testdata-phones.sql")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class JdbcStockDaoTest {
    private static final long PHONE_ID = 1000L;

    @Autowired
    private JdbcStockDao jdbcStockDao;

    @Test
    public void get() {
        Stock stock = jdbcStockDao.get(PHONE_ID);
        assertNotNull(stock.getPhone());
        assertNotNull(stock.getStock());
        assertNotNull(stock.getReserved());
    }

    @Test
    public void getStockList(){
        List<Long> phoneIds = Arrays.asList(1000L, 1001L, 1002L);
        List<Stock> stockList = jdbcStockDao.getStocks(phoneIds);

        assertEquals(phoneIds.size(), stockList.size());
        stockList.stream().map(Stock::getPhone)
                .forEach(phone -> assertThat(phone.getId(), anyOf(is(1000L), is(1001L), is(1002L))));
    }
}