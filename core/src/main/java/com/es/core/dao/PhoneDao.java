package com.es.core.dao;

import com.es.core.model.phone.Phone;

import java.util.List;

public interface PhoneDao {
    Phone get(Long key);
    void save(Phone phone);
    List<Phone> findAll(int offset, int limit);
    List<Phone> findAllValid(int offset, int limit);
    int findValidPhonesTotalCount();
}
