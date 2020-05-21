package com.es.core.dao;

import com.es.core.model.product.Product;

import java.util.List;

 public interface ProductDao<T extends Product> {
    T get(Long key);
    void save(T product);
    List<T> findAll(int offset, int limit);
    List<T> findAllValid(int offset, int limit);
    List<T> findAllValid(int offset, int limit, String query);
    List<T> findAllValid(int offset, int limit, String sortName, String sortOrder);
    List<T> findAllValid(int offset, int limit, String query, String sortName, String sortOrder);
    int findValidTotalCount();
    int findMatchingQueryTotalCount(String query);
}
