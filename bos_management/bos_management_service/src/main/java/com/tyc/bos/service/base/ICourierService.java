package com.tyc.bos.service.base;

import com.tyc.bos.domain.base.Courier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ICourierService {
    void save(Courier courier);

    Page<Courier> pageQuery(Specification<Courier> spct, Pageable pageable);

    void delete(String ids);

    List<Courier> findByDeltagIsNull();
}
