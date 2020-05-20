package com.es.core.service.product;

import com.es.core.model.product.Book;
import org.springframework.data.domain.Page;

public interface ProductService {
    Page<Book> getPage(int currentPage, int pageSize, String sortName, String sortOrder);
    Page<Book> getPage(int currentPage, int pageSize, String query, String sortName, String sortOrder);
    Book get(Long id);
}
