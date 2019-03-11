package com.es.core.service.impl;

import com.es.core.dao.PhoneDao;
import com.es.core.model.phone.Phone;
import com.es.core.service.PhoneService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PhoneServiceImpl implements PhoneService {
    @Value("${phones.defaultSortName}")
    private String defaultPhonesSortName;
    @Value("${phones.defaultSortOrder}")
    private String defaultPhonesSortOrder;
    @Resource(name = "jdbcPhoneDao")
    private PhoneDao phoneDao;

    public Page<Phone> getPage(int currentPage, int pageSize, String sortName, String sortOrder) {
        List<Phone> phonesOnPageList;
        int startItem = currentPage * pageSize;
        int totalSize = phoneDao.findValidPhonesTotalCount();

        phonesOnPageList = phoneDao.findAllValid(startItem - 1, pageSize,
                sortName == null ? defaultPhonesSortName : sortName,
                sortOrder == null ? defaultPhonesSortOrder : sortOrder);

        return new PageImpl<>(phonesOnPageList, PageRequest.of(currentPage, pageSize), totalSize);
    }

    public Page<Phone> getPage(int currentPage, int pageSize, String query, String sortName, String sortOrder) {
        List<Phone> phonesOnPageList;
        int totalSize = phoneDao.findPhonesMatchingQueryTotalCount(query);
        int startItem = currentPage * pageSize;

        phonesOnPageList = phoneDao.findAllValid(startItem - 1, pageSize, query,
                sortName == null ? defaultPhonesSortName : sortName,
                sortOrder == null ? defaultPhonesSortOrder : sortOrder);

        return new PageImpl<>(phonesOnPageList, PageRequest.of(currentPage, pageSize), totalSize);
    }
}
