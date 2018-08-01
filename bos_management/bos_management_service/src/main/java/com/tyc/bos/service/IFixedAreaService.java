package com.tyc.bos.service;

import com.tyc.bos.domain.base.FixedArea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IFixedAreaService {
    void save(FixedArea model);

    Page<FixedArea> pageQuery(Pageable pageable);
}
