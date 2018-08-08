package com.tyc.bos.service.system;

import com.tyc.bos.domain.system.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IMenuService {
    List<Menu> findAll();

    void save(Menu model);

    Page<Menu> pageQuery(Pageable pageable);
}
