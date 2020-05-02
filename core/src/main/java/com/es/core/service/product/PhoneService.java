package com.es.core.service.product;

import com.es.core.model.phone.Phone;
import org.springframework.data.domain.Page;

public interface PhoneService {
    Page<Phone> getPage(int currentPage, int pageSize, String sortName, String sortOrder);
    Page<Phone> getPage(int currentPage, int pageSize, String query, String sortName, String sortOrder);
    Phone getPhone(Long phoneId);
}
