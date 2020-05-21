package com.es.core.service.product;

import com.es.core.dao.ProductDao;
import com.es.core.exception.ProductsNotFoundException;
import com.es.core.model.product.Book;
import org.h2.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService<Book> {
    @Value("${phones.defaultSortName}")
    private String defaultPhonesSortName;

    @Value("${phones.defaultSortOrder}")
    private String defaultPhonesSortOrder;

    @Autowired
    private ProductDao<Book> productDao;

    public Page<Book> getPage(int currentPage, int pageSize, String sortName, String sortOrder) {
        int startItem = currentPage * pageSize;
        int totalSize = productDao.findValidTotalCount();

        List<Book> phonesOnPageList = productDao.findAllValid(startItem - 1, pageSize,
                StringUtils.isNullOrEmpty(sortName) ? defaultPhonesSortName : sortName,
                StringUtils.isNullOrEmpty(sortName) ? defaultPhonesSortOrder : sortOrder);

        return new PageImpl<>(phonesOnPageList, PageRequest.of(currentPage, pageSize), totalSize);
    }

    public Page<Book> getPage(int currentPage, int pageSize, String query, String sortName, String sortOrder) {
        int totalSize = productDao.findMatchingQueryTotalCount(query);
        int startItem = currentPage * pageSize;

        List<Book> phonesOnPageList = productDao.findAllValid(startItem - 1, pageSize, query,
                StringUtils.isNullOrEmpty(sortName) ? defaultPhonesSortName : sortName,
                StringUtils.isNullOrEmpty(sortName) ? defaultPhonesSortOrder : sortOrder);

        return new PageImpl<>(phonesOnPageList, PageRequest.of(currentPage, pageSize), totalSize);
    }

    public Book get(Long id) throws ProductsNotFoundException {
        return productDao.get(id);
    }
}
