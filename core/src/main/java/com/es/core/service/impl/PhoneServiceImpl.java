package com.es.core.service.impl;

import com.es.core.dao.PhoneDao;
import com.es.core.exception.PhonesNotFoundException;
import com.es.core.model.phone.Phone;
import com.es.core.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PhoneServiceImpl implements PhoneService {
    private PhoneDao phoneDao;

    @Autowired
    public PhoneServiceImpl(PhoneDao phoneDao) {
        this.phoneDao = phoneDao;
    }

    public Page<Phone> getPage(int currentPage, int pageSize) {
        List<Phone> phonesOnPageList;
        int totalSize;
        int startItem = currentPage * pageSize;

        if ((totalSize = phoneDao.findValidPhonesTotalCount()) < currentPage * pageSize) {
            phonesOnPageList = Collections.emptyList();
        } else {
            try {
                phonesOnPageList = phoneDao.findAllValid(startItem - 1, pageSize);
            } catch (PhonesNotFoundException e) {
                phonesOnPageList = Collections.emptyList();
            }
        }
        return new PageImpl<>(phonesOnPageList, PageRequest.of(currentPage, pageSize), totalSize);
    }

    public Page<Phone> getPage(int currentPage, int pageSize, String query) {
        List<Phone> phonesOnPageList;
        int totalSize;
        int startItem = currentPage * pageSize;

        if ((totalSize = phoneDao.findPhonesMatchingQueryTotalCount(query)) < currentPage * pageSize) {
            phonesOnPageList = Collections.emptyList();
        } else {
            try {
                phonesOnPageList = phoneDao.findAllValid(startItem - 1, pageSize, query);
            } catch (PhonesNotFoundException e) {
                phonesOnPageList = Collections.emptyList();
            }
        }
        return new PageImpl<>(phonesOnPageList, PageRequest.of(currentPage, pageSize), totalSize);
    }

    public Page<Phone> getPage(int currentPage, int pageSize, String sortName, String sortOrder) {
        List<Phone> phonesOnPageList;
        int totalSize;
        int startItem = currentPage * pageSize;

        if ((totalSize = phoneDao.findValidPhonesTotalCount()) < currentPage * pageSize) {
            phonesOnPageList = Collections.emptyList();
        } else {
            try {
                phonesOnPageList = phoneDao.findAllValid(startItem - 1, pageSize, sortName, sortOrder);
            } catch (PhonesNotFoundException e) {
                phonesOnPageList = Collections.emptyList();
            }
        }
        return new PageImpl<>(phonesOnPageList, PageRequest.of(currentPage, pageSize), totalSize);
    }

    public Page<Phone> getPage(int currentPage, int pageSize, String query, String sortName, String sortOrder) {
        List<Phone> phonesOnPageList;
        int totalSize;
        int startItem = currentPage * pageSize;

        if ((totalSize = phoneDao.findPhonesMatchingQueryTotalCount(query)) < currentPage * pageSize) {
            phonesOnPageList = Collections.emptyList();
        } else {
            try {
                phonesOnPageList = phoneDao.findAllValid(startItem - 1, pageSize, query, sortName, sortOrder);
            } catch (PhonesNotFoundException e) {
                phonesOnPageList = Collections.emptyList();
            }
        }
        return new PageImpl<>(phonesOnPageList, PageRequest.of(currentPage, pageSize), totalSize);
    }


}
