package com.es.core.service.product;

import com.es.core.model.product.Book;
import com.es.core.model.product.Product;
import org.springframework.data.domain.Page;

public interface ProductService<T extends Product> {
    Page<T> getPage(int currentPage, int pageSize, String sortName, String sortOrder);
    Page<T> getPage(int currentPage, int pageSize, String query, String sortName, String sortOrder);
    Book get(Long id);
}
