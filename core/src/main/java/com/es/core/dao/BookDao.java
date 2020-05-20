package com.es.core.dao;

import com.es.core.model.product.Book;

import java.util.List;

public interface BookDao {
    Book get(Long key);
    void save(Book phone);
    List<Book> findAll(int offset, int limit);
    List<Book> findAllValid(int offset, int limit);
    List<Book> findAllValid(int offset, int limit, String query);
    List<Book> findAllValid(int offset, int limit, String sortName, String sortOrder);
    List<Book> findAllValid(int offset, int limit, String query, String sortName, String sortOrder);
    int findValidTotalCount();
    int findMatchingQueryTotalCount(String query);
}
