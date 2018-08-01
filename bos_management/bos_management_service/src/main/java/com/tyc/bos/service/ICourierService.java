package com.tyc.bos.service;

import com.tyc.bos.domain.base.Courier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface ICourierService {
    void save(Courier courier);

    Page<Courier> pageQuery(Specification<Courier> spct, Pageable pageable);

    void delete(String ids);
}
