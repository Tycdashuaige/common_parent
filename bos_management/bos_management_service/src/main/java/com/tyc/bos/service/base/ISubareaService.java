package com.tyc.bos.service.base;

import com.tyc.bos.domain.base.SubArea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ISubareaService {
    void save(SubArea model);

    Page<SubArea> pageQuery(Pageable pageable);

    List<SubArea> findAll();

    List<Object> findBySubareas();
}
