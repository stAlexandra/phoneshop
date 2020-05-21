package com.es.core.dao.impl;

import com.es.core.dao.ProductDao;
import com.es.core.exception.ProductsNotFoundException;
import com.es.core.model.product.Book;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.es.core.dao.DbContract.BooksTable.ID;

@Repository
public class JdbcBookDaoImpl implements ProductDao<Book> {
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM books WHERE id = :id";
    private static final String SQL_INSERT = "INSERT INTO books VALUES (:id, :name, :description, :imageUrl, :price, :author, " +
            ":genre, :dateOfPublishing, :publisher, :pageCount)";
    private static final String SQL_UPDATE = "UPDATE books SET name=:name, description=:description, imageUrl=:imageUrl, price=:price, author=:author, " +
            "genre=:genre, dateOfPublishing=:dateOfPublishing, publisher=:publisher, pageCount=:pageCount WHERE id=:id";
    private static final String SQL_FIND_ALL = "SELECT * FROM books OFFSET :offset LIMIT :limit";
    private static final String SQL_FIND_ALL_PRESENT_IN_STOCK = "SELECT b.* FROM books b " +
            "JOIN stocks st ON b.id = st.productId WHERE st.stock - st.reserved > 0 " +
            "AND b.price > 0 " +
            "OFFSET :offset LIMIT :limit";
    private static final String SQL_FIND_VALID_QUERY = "SELECT b.* FROM books b " +
            "JOIN stocks st ON b.id = st.productId WHERE st.stock - st.reserved > 0 " +
            "AND b.price > 0 " +
            "AND (b.name ILIKE CONCAT('%', :query, '%') OR b.author ILIKE CONCAT('%', :query, '%')) " +
            "OFFSET :offset LIMIT :limit";
    private static final String SQL_FIND_VALID_SORT = "SELECT b.* FROM books b " +
            "JOIN stocks st ON b.id = st.productId WHERE st.stock - st.reserved > 0 " +
            "AND b.price > 0 " +
            "ORDER BY &sortName &sortOrder OFFSET :offset LIMIT :limit";
    private static final String SQL_FIND_VALID_QUERY_SORT = "SELECT b.* FROM books b " +
            "JOIN stocks st ON b.id = st.productId WHERE st.stock - st.reserved > 0 " +
            "AND b.price > 0 " +
            "AND (b.name ILIKE CONCAT('%', :query, '%') OR b.author ILIKE CONCAT('%', :query, '%')) " +
            "ORDER BY &sortName &sortOrder OFFSET :offset LIMIT :limit";
    private static final String SQL_COUNT_VALID = "SELECT COUNT(productId) AS count FROM stocks st " +
            "JOIN books b ON b.id = st.productId " +
            "WHERE st.stock - st.reserved > 0 AND b.price > 0";
    private static final String SQL_COUNT_VALID_MATCHING_QUERY = "SELECT COUNT(productId) AS count FROM stocks st " +
            "JOIN books b ON b.id = st.productId " +
            "WHERE st.stock - st.reserved > 0 AND b.price > 0" +
            "AND (b.name ILIKE CONCAT('%', :query, '%') OR b.author ILIKE CONCAT('%', :query, '%'))";

    private static final String COUNT_COLUMN_LABEL = "count";
    private static final String QUERY_PARAM = "query";
    private static final String OFFSET_PARAM = "offset";
    private static final String LIMIT_PARAM = "limit";
    private static final String SORT_NAME_REPLACE_WORD = "&sortName";
    private static final String SORT_ORDER_REPLACE_WORD = "&sortOrder";

    @Resource
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Book get(Long key) {
        Map<String, Long> namedParameters = Collections.singletonMap(ID, key);

        List<Book> bookList = jdbcTemplate.query(SQL_SELECT_BY_ID, namedParameters, new BeanPropertyRowMapper<>(Book.class));
        if (CollectionUtils.isEmpty(bookList) || bookList.size() > 1) {
            throw new ProductsNotFoundException(key);
        }
        return bookList.get(0);
    }

    @Override
    public void save(Book book) {
        if (book.getName() == null || book.getAuthor() == null) {
            throw new IllegalArgumentException("Trying to save phone with empty required fields.");
        }
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource bookParam = new BeanPropertySqlParameterSource(book);
        if (book.getId() == null) {
            jdbcTemplate.update(SQL_INSERT, bookParam, keyHolder);
            book.setId(keyHolder.getKey().longValue());
        } else {
            jdbcTemplate.update(SQL_UPDATE, bookParam);
        }

    }

    @Override
    public List<Book> findAll(int offset, int limit) {
        return findBooks(SQL_FIND_ALL, offset, limit, new MapSqlParameterSource());
    }

    @Override
    public List<Book> findAllValid(int offset, int limit) {
        return findBooks(SQL_FIND_ALL_PRESENT_IN_STOCK, offset, limit, new MapSqlParameterSource());
    }

    @Override
    public List<Book> findAllValid(int offset, int limit, String query) {
        return findBooks(SQL_FIND_VALID_QUERY, offset, limit, new MapSqlParameterSource(QUERY_PARAM, query));
    }

    @Override
    public List<Book> findAllValid(int offset, int limit, String sortName, String sortOrder) {
        return findBooks(SQL_FIND_VALID_SORT.replaceFirst(SORT_NAME_REPLACE_WORD, sortName)
                .replaceFirst(SORT_ORDER_REPLACE_WORD, sortOrder), offset, limit, new MapSqlParameterSource());
    }

    @Override
    public List<Book> findAllValid(int offset, int limit, String query, String sortName, String sortOrder) {
        return findBooks(SQL_FIND_VALID_QUERY_SORT.replaceFirst(SORT_NAME_REPLACE_WORD, sortName)
                        .replaceFirst(SORT_ORDER_REPLACE_WORD, sortOrder), offset, limit,
                new MapSqlParameterSource(QUERY_PARAM, query));
    }

    @Override
    public int findValidTotalCount() {
        return jdbcTemplate.query(SQL_COUNT_VALID, resultSet ->
                resultSet.next() ? resultSet.getInt(COUNT_COLUMN_LABEL) : 0);
    }

    @Override
    public int findMatchingQueryTotalCount(String query) {
        return jdbcTemplate.query(SQL_COUNT_VALID_MATCHING_QUERY,
                Collections.singletonMap(QUERY_PARAM, query),
                resultSet -> resultSet.next() ? resultSet.getInt(COUNT_COLUMN_LABEL) : 0);
    }

    private List<Book> findBooks(String sql, int offset, int limit, MapSqlParameterSource params) {
        params.addValue(OFFSET_PARAM, offset);
        params.addValue(LIMIT_PARAM, limit);

        List<Book> bookList = jdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(Book.class));
        return bookList == null ? new ArrayList<>() : bookList;
    }
}
