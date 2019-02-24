package com.es.core.service;

import com.es.core.model.phone.Phone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PhoneService {
    Page<Phone> findPaginated(Pageable pageable);
}
