package com.tyc.bos.service.system;

import com.tyc.bos.domain.system.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPermissionService {

    Page<Permission> pageQuery(Pageable pageable);

    void save(Permission model);

    List<Permission> findAll();

}
