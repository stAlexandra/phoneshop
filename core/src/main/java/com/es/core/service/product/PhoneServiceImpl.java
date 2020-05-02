package com.es.core.service.product;

import com.es.core.dao.PhoneDao;
import com.es.core.exception.PhonesNotFoundException;
import com.es.core.model.phone.Phone;
import org.h2.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhoneServiceImpl implements PhoneService {
    @Value("${phones.defaultSortName}")
    private String defaultPhonesSortName;

    @Value("${phones.defaultSortOrder}")
    private String defaultPhonesSortOrder;

    @Autowired
    private PhoneDao phoneDao;

    public Page<Phone> getPage(int currentPage, int pageSize, String sortName, String sortOrder) {
        int startItem = currentPage * pageSize;
        int totalSize = phoneDao.findValidPhonesTotalCount();

        List<Phone> phonesOnPageList = phoneDao.findAllValid(startItem - 1, pageSize,
                StringUtils.isNullOrEmpty(sortName) ? defaultPhonesSortName : sortName,
                StringUtils.isNullOrEmpty(sortName) ? defaultPhonesSortOrder : sortOrder);

        return new PageImpl<>(phonesOnPageList, PageRequest.of(currentPage, pageSize), totalSize);
    }

    public Page<Phone> getPage(int currentPage, int pageSize, String query, String sortName, String sortOrder) {
        int totalSize = phoneDao.findPhonesMatchingQueryTotalCount(query);
        int startItem = currentPage * pageSize;

        List<Phone> phonesOnPageList = phoneDao.findAllValid(startItem - 1, pageSize, query,
                StringUtils.isNullOrEmpty(sortName) ? defaultPhonesSortName : sortName,
                StringUtils.isNullOrEmpty(sortName) ? defaultPhonesSortOrder : sortOrder);

        return new PageImpl<>(phonesOnPageList, PageRequest.of(currentPage, pageSize), totalSize);
    }

    public Phone getPhone(Long phoneId) throws PhonesNotFoundException {
        return phoneDao.get(phoneId);
    }
}
