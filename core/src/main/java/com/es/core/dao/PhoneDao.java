package com.es.core.dao;

import com.es.core.model.phone.Phone;

import java.util.List;

public interface PhoneDao {
    Phone get(Long key);
    void save(Phone phone);
    List<Phone> findAll(int offset, int limit);
    List<Phone> findAllValid(int offset, int limit);
    List<Phone> findAllValid(int offset, int limit, String query);
    List<Phone> findAllValid(int offset, int limit, String sortName, String sortOrder);
    List<Phone> findAllValid(int offset, int limit, String query, String sortName, String sortOrder);
    int findValidPhonesTotalCount();
    int findPhonesMatchingQueryTotalCount(String query);
}
